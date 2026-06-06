package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private Integer totalQuantityIssued;

    @Column(nullable = false)
    private Integer dailyDosageInstructions; // Number of pills per day

    @Column(columnDefinition = "TEXT")
    private String specialClinicalDirectives;

    private LocalDateTime issuedAt;

    @PrePersist
    protected void onCreate() {
        this.issuedAt = LocalDateTime.now();
    }
}
