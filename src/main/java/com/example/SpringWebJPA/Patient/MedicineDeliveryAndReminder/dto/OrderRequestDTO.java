package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long patientId;
    private String medicineName;
    private Integer totalQuantity;
    private Integer dailyDosageCount;
}