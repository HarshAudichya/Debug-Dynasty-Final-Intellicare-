package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuePrescriptionDTO {
    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String medicineName;
    private Integer totalQuantityIssued;
    private Integer dailyDosageInstructions;
    private String specialClinicalDirectives;
}
