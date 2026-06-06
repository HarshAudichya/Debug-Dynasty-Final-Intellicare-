package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_ambulances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmbulanceTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String operationalStatus; // AVAILABLE, EN_ROUTE_TO_PATIENT, RETURNING_TO_ER, MAINTENANCE

    private String currentDriverName;
}
