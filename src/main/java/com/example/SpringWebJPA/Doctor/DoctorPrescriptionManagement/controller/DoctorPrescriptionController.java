package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.controller;

import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto.IssuePrescriptionDTO;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.dto.PrescriptionReceiptDTO;
import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.service.PrescriptionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/prescriptions")
@RequiredArgsConstructor
public class DoctorPrescriptionController {
    private final PrescriptionManagementService prescriptionService;

    @PostMapping("/issue")
    public ResponseEntity<PrescriptionReceiptDTO> issuePrescriptionToPatient(@RequestBody IssuePrescriptionDTO request) {
        return ResponseEntity.ok(prescriptionService.writeDigitalPrescription(request));
    }
}
