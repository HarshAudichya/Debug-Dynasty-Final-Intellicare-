package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.controller;

import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto.CreateSlotDTO;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto.BookingRequestDTO;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.DoctorSlot;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.Appointment;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/scheduling")
@RequiredArgsConstructor
public class DoctorSchedulingController {
    private final SchedulingService schedulingService;

    @PostMapping("/create-slot")
    public ResponseEntity<DoctorSlot> addAvailability(@RequestBody CreateSlotDTO request) {
        return ResponseEntity.ok(schedulingService.generateAvailabilitySlot(request));
    }

    @PostMapping("/book-appointment")
    public ResponseEntity<Appointment> requestBooking(@RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(schedulingService.allocateAppointment(request));
    }
}
