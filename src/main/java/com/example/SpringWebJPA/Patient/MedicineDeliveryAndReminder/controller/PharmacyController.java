package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.controller;

import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto.OrderRequestDTO;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.dto.DeliveryStatusDTO;
import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @PostMapping("/order")
    public ResponseEntity<DeliveryStatusDTO> purchaseMedication(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(pharmacyService.placeOrder(request));
    }

    @PostMapping("/test-sweep")
    public ResponseEntity<String> forceTriggerReminderScan() {
        pharmacyService.executeAutomatedRefillEvaluationSweep();
        return ResponseEntity.ok("Cron sweep cycle triggered manually for verification logs.");
    }
}
