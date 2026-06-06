package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInDTO {
    private Long recoveryPlanId;
    private Integer painScale;
    private boolean hasFever;
    private String patientNotes;
}
