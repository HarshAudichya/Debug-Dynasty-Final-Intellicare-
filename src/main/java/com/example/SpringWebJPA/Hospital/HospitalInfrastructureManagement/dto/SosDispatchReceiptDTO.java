package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SosDispatchReceiptDTO {
    private String status;
    private String assignedAmbulanceVehicle;
    private String reservedErBedCode;
    private String bloodBankStatusNotice;
    private String dispatchLogMessage;
}