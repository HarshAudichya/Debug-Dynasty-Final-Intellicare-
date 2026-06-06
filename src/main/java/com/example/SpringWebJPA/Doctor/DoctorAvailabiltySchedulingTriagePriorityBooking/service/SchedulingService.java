package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.service;

import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto.CreateSlotDTO;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto.BookingRequestDTO;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.DoctorSlot;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.Appointment;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository.DoctorSlotRepository;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final DoctorSlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public DoctorSlot generateAvailabilitySlot(CreateSlotDTO dto) {
        DoctorSlot slot = DoctorSlot.builder()
                .doctorId(dto.getDoctorId())
                .slotStartTime(dto.getSlotStartTime())
                .slotEndTime(dto.getSlotEndTime())
                .build();
        return slotRepository.save(slot);
    }

    @Transactional
    public Appointment allocateAppointment(BookingRequestDTO dto) {
        List<DoctorSlot> availableSlots = slotRepository.findAvailableSlotsByDoctorSorted(dto.getDoctorId());

        if (availableSlots.isEmpty()) {
            throw new IllegalStateException("Scheduling Failure: No slots available for the selected doctor.");
        }

        DoctorSlot targetedSlot;

        // Triage Allocation Rule: Critical/Urgent priority shifts routing to the earliest open block
        if ("CRITICAL".equalsIgnoreCase(dto.getIncomingTriagePriority()) || "URGENT".equalsIgnoreCase(dto.getIncomingTriagePriority())) {
            targetedSlot = availableSlots.get(0);
        } else {
            targetedSlot = availableSlots.get(availableSlots.size() - 1); // Keep earlier slots free for emergencies
        }

        targetedSlot.setBooked(true);
        slotRepository.save(targetedSlot);

        Appointment appointment = Appointment.builder()
                .doctorId(dto.getDoctorId())
                .patientId(dto.getPatientId())
                .slotId(targetedSlot.getId())
                .scheduledTime(targetedSlot.getSlotStartTime())
                .triagePriority(dto.getIncomingTriagePriority().toUpperCase())
                .build();

        return appointmentRepository.save(appointment);
    }
}
