package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_daily_checkins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyCheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recoveryPlanId;

    @Column(nullable = false)
    private Integer painScale; // On a strict numeric metric scale of 1-10

    @Column(nullable = false)
    private boolean hasFever;

    @Column(columnDefinition = "TEXT")
    private String patientNotes; // Symptoms typed by patient

    @Column(nullable = false)
    private String assessmentFlag; // ON_TRACK, CAUTION, FLAG_PHYSICIAN

    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
        this.assessmentFlag = "ON_TRACK";
    }
}
