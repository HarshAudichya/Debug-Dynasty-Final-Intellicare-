package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_blood_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bloodType; // O+, A-, B+, etc.

    @Column(nullable = false)
    private Integer unitsAvailable;
}
