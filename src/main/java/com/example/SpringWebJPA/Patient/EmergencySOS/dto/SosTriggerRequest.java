package com.example.SpringWebJPA.Patient.EmergencySOS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SosTriggerRequest {
    private Long patientId;
    private Double latitude;
    private Double longitude;
}