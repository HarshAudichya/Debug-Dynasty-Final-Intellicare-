package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowUpReportDTO {
    private Long checkInId;
    private String statusFlag;
    private String clinicalGuidance;
    private LocalDateTime checkedAt;
}
