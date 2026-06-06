package com.example.SpringWebJPA.Doctor.DoctorDashboard.controller;

import com.example.SpringWebJPA.Doctor.DoctorDashboard.dto.DoctorDashboardDTO;
import com.example.SpringWebJPA.Doctor.DoctorDashboard.service.DoctorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/dashboard")
@RequiredArgsConstructor
public class DoctorDashboardController {
    private final DoctorDashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DoctorDashboardDTO> getDoctorDashboardSummary(@RequestParam Long doctorId) {
        return ResponseEntity.ok(dashboardService.compileDashboardView(doctorId));
    }
}