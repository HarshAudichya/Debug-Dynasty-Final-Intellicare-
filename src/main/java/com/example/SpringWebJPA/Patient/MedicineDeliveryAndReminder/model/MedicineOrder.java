package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicine_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer dailyDosageCount;

    @Column(nullable = false)
    private String status;

    private LocalDateTime orderedAt;
    private LocalDateTime deliveryDate;

    @PrePersist
    protected void onCreate() {
        this.orderedAt = LocalDateTime.now();
        this.status = "ORDERED";
    }
}
