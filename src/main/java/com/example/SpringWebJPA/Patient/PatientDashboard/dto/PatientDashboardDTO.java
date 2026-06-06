package com.example.SpringWebJPA.Patient.PatientDashboard.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDashboardDTO {
    private Long patientId;

    // Aggregated Sub-Module States
    private Object currentRecoveryPlan;            // From Module 7
    private List<Object> activeMedicineOrders;     // From Module 6
    private List<Object> pendingRefillReminders;   // From Module 6
    private Object activeBloodRequest;              // From Module 5

    private String dashboardStatusSummary;          // Quick diagnostic summary text
}
