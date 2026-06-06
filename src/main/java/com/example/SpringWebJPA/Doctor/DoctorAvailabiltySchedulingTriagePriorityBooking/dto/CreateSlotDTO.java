package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotDTO {
    private Long doctorId;
    private LocalDateTime slotStartTime;
    private LocalDateTime slotEndTime;
}