package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private Long patientId;
    private Long doctorId;
    private String incomingTriagePriority; // CRITICAL, URGENT, ROUTINE
}
