package com.example.SpringWebJPA.Patient.BloodRequest.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequestDTO {
    private Long patientId;
    private String bloodType;
    private Integer unitsRequested;
}
