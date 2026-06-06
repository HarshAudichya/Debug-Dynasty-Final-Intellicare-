package com.example.SpringWebJPA.Patient.PatientDashboard.service;

import com.example.SpringWebJPA.Patient.BloodRequest.repository.BloodRequestRepository;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.RecoveryPlan;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository.RecoveryPlanRepository;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository.MedicineOrderRepository;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository.RefillReminderRepository;
import com.example.SpringWebJPA.Patient.PatientDashboard.dto.PatientDashboardDTO;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardAggregationService {

    // Wire up existing repositories to harvest records
    private final RecoveryPlanRepository planRepository;
    private final MedicineOrderRepository orderRepository;
    private final RefillReminderRepository reminderRepository;
    private final BloodRequestRepository bloodRequestRepository;

    @Transactional(readOnly = true)
    public PatientDashboardDTO compileDashboard(Long patientId) {

        // 1. Harvest Recovery Progress Node (Module 7)
        Object recoveryPlan = planRepository.findFirstByPatientIdOrderByInitializedAtDesc(patientId)
                .orElse(null);

        // 2. Harvest Prescription Delivery & Alerts (Module 6)
        var orders = orderRepository.findAll().stream()
                .filter(order -> order.getPatientId().equals(patientId))
                .collect(Collectors.toList());

        var reminders = reminderRepository.findAll().stream()
                .filter(rem -> rem.getPatientId().equals(patientId) && !rem.isReminderSent())
                .collect(Collectors.toList());

        // 3. Harvest Blood Request Matrix (Module 5)
        Object bloodRequest = bloodRequestRepository.findFirstByPatientIdOrderByCreatedAtDesc(patientId)
                .orElse(null);

        // 4. Run Cross-Module Diagnostic Reasoning Engine
        String statusSummary = "ALL SYSTEMS NOMINAL: Continue standard rehabilitation routines.";

        // Example check: If recovery is complicated and medicine is running low, escalate dashboard status immediately
        boolean hasComplications = recoveryPlan != null &&
                ((RecoveryPlan) recoveryPlan)
                        .getMonitoringStatus().equals("COMPLICATED");

        boolean hasPendingReminders = !reminders.isEmpty();

        if (hasComplications && hasPendingReminders) {
            statusSummary = "ACTION REQUIRED: Recovery plan indicates clinical complications and medication supplies are running out. Re-order immediately.";
        } else if (hasComplications) {
            statusSummary = "CLINICAL CAUTION: Recovery plan flags anomalies. Rest and closely monitor symptoms.";
        } else if (hasPendingReminders) {
            statusSummary = "NOTICE: You have pending prescription refills coming due within 7 days.";
        }

        return PatientDashboardDTO.builder()
                .patientId(patientId)
                .currentRecoveryPlan(recoveryPlan)
                .activeMedicineOrders(orders.stream().map(o -> (Object)o).collect(Collectors.toList()))
                .pendingRefillReminders(reminders.stream().map(r -> (Object)r).collect(Collectors.toList()))
                .activeBloodRequest(bloodRequest)
                .dashboardStatusSummary(statusSummary)
                .build();
    }
}
