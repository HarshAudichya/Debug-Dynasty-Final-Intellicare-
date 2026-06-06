package com.example.SpringWebJPA.Patient.EmergencySOS.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private String status; // TRIGGERED, AMBULANCE_DISPATCHED, EN_ROUTE, ARRIVED, COMPLETED

    private Long assignedAmbulanceId;
    private Long targetHospitalId;

    private LocalDateTime triggeredAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onTrigger() {
        this.triggeredAt = LocalDateTime.now();
        this.status = "TRIGGERED";
    }
}