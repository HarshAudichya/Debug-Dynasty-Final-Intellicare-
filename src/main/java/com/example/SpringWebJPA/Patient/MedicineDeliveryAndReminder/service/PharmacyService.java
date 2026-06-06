package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.service;

import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto.OrderRequestDTO;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto.DeliveryStatusDTO;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model.MedicineOrder;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model.RefillReminder;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository.MedicineOrderRepository;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository.RefillReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final MedicineOrderRepository orderRepository;
    private final RefillReminderRepository reminderRepository;

    @Transactional
    public DeliveryStatusDTO placeOrder(OrderRequestDTO dto) {
        // 1. Persist order details
        MedicineOrder order = MedicineOrder.builder()
                .patientId(dto.getPatientId())
                .medicineName(dto.getMedicineName())
                .totalQuantity(dto.getTotalQuantity())
                .dailyDosageCount(dto.getDailyDosageCount())
                .deliveryDate(LocalDateTime.now().plusDays(2))
                .build();
        orderRepository.save(order);

        // 2. Mathematically compute prescription depletion timeline
        int operationalDaysRemaining = dto.getTotalQuantity() / dto.getDailyDosageCount();
        LocalDate depletionDate = LocalDate.now().plusDays(operationalDaysRemaining);

        // 3. Log alert parameters into checking register
        RefillReminder reminder = RefillReminder.builder()
                .patientId(dto.getPatientId())
                .medicineName(dto.getMedicineName())
                .expectedDepletionDate(depletionDate)
                .isReminderSent(false)
                .build();
        reminderRepository.save(reminder);

        return DeliveryStatusDTO.builder()
                .orderId(order.getId())
                .medicineName(order.getMedicineName())
                .status(order.getStatus())
                .estimatedDelivery(order.getDeliveryDate())
                .build();
    }

    // Cron runs automatically at midnight every single day to evaluate stock remaining
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void executeAutomatedRefillEvaluationSweep() {
        LocalDate thresholdDate = LocalDate.now().plusDays(7); // Check for refills within 7 days
        List<RefillReminder> criticalReminders = reminderRepository.findPendingReminders(thresholdDate);

        for (RefillReminder alertNode : criticalReminders) {
            System.out.println("ALERT TRIGGERED: Patient " + alertNode.getPatientId() +
                    ", your supply of " + alertNode.getMedicineName() +
                    " runs out on " + alertNode.getExpectedDepletionDate() + ". Order your refill now!");

            alertNode.setReminderSent(true);
            reminderRepository.save(alertNode);
        }
    }
}