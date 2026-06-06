package com.example.SpringWebJPA.Doctor.DoctorDashboard.service;

import com.example.SpringWebJPA.Doctor.DoctorDashboard.dto.DoctorDashboardDTO;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.Appointment;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository.AppointmentRepository;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository.DoctorSlotRepository;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.repository.DigitalPrescriptionRepository; // Cross-package wire
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorDashboardService {

    private final DoctorSlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;
    private final DigitalPrescriptionRepository prescriptionRepository; // Extracted from your prescription package

    @Transactional(readOnly = true)
    public DoctorDashboardDTO compileDashboardView(Long doctorId) {

        // 1. Fetch open, unbooked time slots
        var openSlots = slotRepository.findAvailableSlotsByDoctorSorted(doctorId).stream()
                .map(slot -> (Object) slot)
                .collect(Collectors.toList());

        // 2. Fetch historical prescription records issued by this doctor
        var prescriptions = prescriptionRepository.findByDoctorId(doctorId).stream()
                .map(prescription -> (Object) prescription)
                .collect(Collectors.toList());

        // 3. Fetch active appointments and apply a Triage-Priority sorting algorithm
        List<Appointment> rawAppointments = appointmentRepository.findAll().stream()
                .filter(app -> app.getDoctorId().equals(doctorId) && "SCHEDULED".equalsIgnoreCase(app.getStatus()))
                .collect(Collectors.toList());

        // Custom Comparator: CRITICAL = Priority 1, URGENT = Priority 2, ROUTINE = Priority 3
        List<Appointment> prioritizedAppointments = rawAppointments.stream()
                .sorted(Comparator.comparing((Appointment app) -> {
                    String priority = app.getTriagePriority();
                    if ("CRITICAL".equalsIgnoreCase(priority)) return 1;
                    if ("URGENT".equalsIgnoreCase(priority)) return 2;
                    return 3; // Routine cases
                }).thenComparing(Appointment::getScheduledTime)) // Secondary sort by appointment time
                .collect(Collectors.toList());

        // 4. Calculate Emergency Workload Telemetry
        long emergencyCount = prioritizedAppointments.stream()
                .filter(app -> "CRITICAL".equalsIgnoreCase(app.getTriagePriority()) || "URGENT".equalsIgnoreCase(app.getTriagePriority()))
                .count();

        // 5. Generate dynamically tailored operational message headers
        String notice = "ROUTINE CLINICAL PACING: Shift balance looks stable.";
        if (emergencyCount >= 3) {
            notice = "HIGH EMERGENCY LOAD ALERT: Severe triage backlogs detected. Please consider opening fallback emergency slots.";
        } else if (emergencyCount > 0) {
            notice = "PRIORITY ATTENTION REQUIRED: Urgent/Critical triage entries are waiting at the front of your queue.";
        }

        return DoctorDashboardDTO.builder()
                .doctorId(doctorId)
                .openAvailabilitySlots(openSlots)
                .sortedAppointments(prioritizedAppointments.stream().map(a -> (Object) a).collect(Collectors.toList()))
                .recentlyIssuedPrescriptions(prescriptions)
                .emergencyQueueCount((int) emergencyCount)
                .operationalNotice(notice)
                .build();
    }
}