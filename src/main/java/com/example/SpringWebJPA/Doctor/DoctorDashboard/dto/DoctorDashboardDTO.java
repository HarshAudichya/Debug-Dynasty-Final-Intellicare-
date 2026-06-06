package com.example.SpringWebJPA.Doctor.DoctorDashboard.dto;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDashboardDTO {
    private Long doctorId;

    // Categorized and Sorted Aggregations
    private List<Object> openAvailabilitySlots;   // Available times
    private List<Object> sortedAppointments;       // Active patients sorted by Triage priority
    private List<Object> recentlyIssuedPrescriptions; // Historical records from Module 9

    private Integer emergencyQueueCount;           // Total count of CRITICAL/URGENT patients
    private String operationalNotice;              // System-generated status alert
}
