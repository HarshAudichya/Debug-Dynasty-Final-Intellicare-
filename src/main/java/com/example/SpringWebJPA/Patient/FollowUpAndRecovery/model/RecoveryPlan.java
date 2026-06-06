package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recovery_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecoveryPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String treatmentType; // e.g., "POST_SURGERY_APPENDICECTOMY", "CARDIAC_RECOVERY"

    @Column(nullable = false)
    private Integer targetRecoveryDays;

    @Column(nullable = false)
    private String monitoringStatus; // ACTIVE, COMPLETED, COMPLICATED

    private LocalDateTime initializedAt;

    @PrePersist
    protected void onCreate() {
        this.initializedAt = LocalDateTime.now();
        this.monitoringStatus = "ACTIVE";
    }
}
