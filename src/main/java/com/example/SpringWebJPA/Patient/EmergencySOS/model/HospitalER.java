package com.example.SpringWebJPA.Patient.EmergencySOS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_er_nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalER {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hospitalName;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private String endpointUrl; // The endpoint to send telemetry pre-alerts to

    private int availableBeds;
}