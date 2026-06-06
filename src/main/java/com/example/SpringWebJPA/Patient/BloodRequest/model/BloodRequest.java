package com.example.SpringWebJPA.Patient.BloodRequest.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String bloodType; // e.g., "O+", "A-", "AB+"

    @Column(nullable = false)
    private Integer unitsRequested;

    private String receiptStorageUrl; // Path to verified blood request receipt document

    @Column(nullable = false)
    private boolean isReceiptVerified;

    @Column(nullable = false)
    private String status; // SUBMITTED, DONOR_MATCHED, FULFILLED, CANCELLED

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = "SUBMITTED";
        this.isReceiptVerified = false; // Requires administrative validation or OCR flag
    }
}
