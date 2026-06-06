package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.controller;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto.CheckInDTO;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto.FollowUpReportDTO;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.RecoveryPlan;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.service.RecoveryCheckerEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/followup")
@RequiredArgsConstructor
public class FollowUpController {
    private final RecoveryCheckerEngine recoveryEngine;

    @PostMapping("/initialize")
    public ResponseEntity<RecoveryPlan> createPlan(
            @RequestParam Long patientId,
            @RequestParam String treatmentType,
            @RequestParam Integer targetDays) {
        return ResponseEntity.ok(recoveryEngine.initializeNewTracker(patientId, treatmentType, targetDays));
    }

    @PostMapping("/checkin")
    public ResponseEntity<FollowUpReportDTO> submitCheckIn(@RequestBody CheckInDTO request) {
        return ResponseEntity.ok(recoveryEngine.evaluateDailyCheckIn(request));
    }
}