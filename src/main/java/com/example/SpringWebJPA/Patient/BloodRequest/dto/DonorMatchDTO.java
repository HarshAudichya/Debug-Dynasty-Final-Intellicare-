package com.example.SpringWebJPA.Patient.BloodRequest.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonorMatchDTO {
    private Long donorId;
    private String fullName;
    private String bloodType;
    private String contactNumber;
}
