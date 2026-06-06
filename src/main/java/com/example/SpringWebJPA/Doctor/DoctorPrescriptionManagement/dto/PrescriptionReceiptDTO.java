package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionReceiptDTO {
    private Long prescriptionId;
    private String medicineName;
    private String calculatedRefillAlertDate;
    private String statusMessage;
    private LocalDateTime issuedAt;
}