package com.example.SpringWebJPA.Patient.EmergencySOS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SosResponseDTO {
    private Long incidentId;
    private String status;
    private String assignedAmbulanceVehicle;
    private Double ambulanceLatitude;
    private Double ambulanceLongitude;
    private String alertedHospitalName;
}