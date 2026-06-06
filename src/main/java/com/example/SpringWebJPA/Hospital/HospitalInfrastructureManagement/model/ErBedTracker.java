package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_er_beds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErBedTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bedCode; // ER-BED-01, ER-BED-02

    @Column(nullable = false)
    private boolean isOccupied;

    private Long assignedPatientId; // Holds ID if reserved or filled
}
