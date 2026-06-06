package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "refill_reminders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefillReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private LocalDate expectedDepletionDate;

    @Column(nullable = false)
    private boolean isReminderSent;
}
