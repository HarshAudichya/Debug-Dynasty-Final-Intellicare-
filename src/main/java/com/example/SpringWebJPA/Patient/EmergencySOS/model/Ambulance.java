package com.example.SpringWebJPA.Patient.EmergencySOS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ambulances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    private Double currentLatitude;
    private Double currentLongitude;

    private boolean isAvailable;
}