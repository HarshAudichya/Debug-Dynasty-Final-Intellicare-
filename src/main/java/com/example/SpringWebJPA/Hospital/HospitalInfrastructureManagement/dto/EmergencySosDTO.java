package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencySosDTO {
    private Long patientId;
    private String patientBloodType;
    private String currentGpsCoordinates;
    private String emergencyNotes;
}
