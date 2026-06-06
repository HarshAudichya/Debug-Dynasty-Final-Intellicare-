package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto;import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusDTO {
    private Long orderId;
    private String medicineName;
    private String status;
    private LocalDateTime estimatedDelivery;
}
