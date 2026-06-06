package com.example.SpringWebJPA.Patient.PatientDashboard.controller;

import com.example.SpringWebJPA.Patient.PatientDashboard.dto.PatientDashboardDTO;
import com.example.SpringWebJPA.Patient.PatientDashboard.service.DashboardAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardAggregationService aggregationService;

    @GetMapping("/patient-summary")
    public ResponseEntity<PatientDashboardDTO> getPatientDashboardView(@RequestParam Long patientId) {
        return ResponseEntity.ok(aggregationService.compileDashboard(patientId));
    }
}
