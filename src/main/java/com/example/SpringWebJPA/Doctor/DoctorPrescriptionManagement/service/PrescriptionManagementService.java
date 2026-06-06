package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.service;

import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto.IssuePrescriptionDTO;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto.PrescriptionReceiptDTO;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.model.DigitalPrescription;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.repository.DigitalPrescriptionRepository;

// Cross-Package Imports connecting Doctor actions directly to Patient reminders
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model.RefillReminder;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository.RefillReminderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PrescriptionManagementService {
    private final DigitalPrescriptionRepository prescriptionRepository;
    private final RefillReminderRepository reminderRepository; // Wired from MedicineDeliveryAndReminder package

    @Transactional
    public PrescriptionReceiptDTO writeDigitalPrescription(IssuePrescriptionDTO dto) {

        // 1. Commit Doctor's authorized clinical prescription note to the database
        DigitalPrescription prescription = DigitalPrescription.builder()
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .diagnosis(dto.getDiagnosis())
                .medicineName(dto.getMedicineName())
                .totalQuantityIssued(dto.getTotalQuantityIssued())
                .dailyDosageInstructions(dto.getDailyDosageInstructions())
                .specialClinicalDirectives(dto.getSpecialClinicalDirectives())
                .build();
        prescriptionRepository.save(prescription);

        // 2. Automation Chain Calculation: Compute exactly when medicine runs thin
        int operationalDaysRemaining = dto.getTotalQuantityIssued() / dto.getDailyDosageInstructions();
        LocalDate targetDepletionDate = LocalDate.now().plusDays(operationalDaysRemaining);

        // 3. Inject the calculated tracker parameters directly into the Patient Reminder system
        RefillReminder automatedReminderNode = RefillReminder.builder()
                .patientId(dto.getPatientId())
                .medicineName(dto.getMedicineName())
                .expectedDepletionDate(targetDepletionDate)
                .isReminderSent(false)
                .build();
        reminderRepository.save(automatedReminderNode);

        return PrescriptionReceiptDTO.builder()
                .prescriptionId(prescription.getId())
                .medicineName(prescription.getMedicineName())
                .calculatedRefillAlertDate(targetDepletionDate.toString())
                .statusMessage("Digital prescription logged successfully. Patient refill reminder scheduled.")
                .issuedAt(prescription.getIssuedAt())
                .build();
    }
}