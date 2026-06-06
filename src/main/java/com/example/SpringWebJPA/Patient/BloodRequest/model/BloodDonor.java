package com.example.SpringWebJPA.Patient.BloodRequest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blood_donors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodDonor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String bloodType;

    @Column(nullable = false)
    private String contactNumber;

    private Double latitude;
    private Double longitude;

    private boolean isAvailable;
}
