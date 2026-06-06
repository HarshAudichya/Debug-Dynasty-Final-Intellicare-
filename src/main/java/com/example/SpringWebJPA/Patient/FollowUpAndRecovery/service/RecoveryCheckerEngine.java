package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.service;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto.CheckInDTO;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.dto.FollowUpReportDTO;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.DailyCheckIn;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.RecoveryPlan;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository.DailyCheckInRepository;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository.RecoveryPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecoveryCheckerEngine {

    private final RecoveryPlanRepository planRepository;
    private final DailyCheckInRepository checkInRepository;

    @Transactional
    public RecoveryPlan initializeNewTracker(Long patientId, String treatment, Integer days) {
        RecoveryPlan plan = RecoveryPlan.builder()
                .patientId(patientId)
                .treatmentType(treatment)
                .targetRecoveryDays(days)
                .build();
        return planRepository.save(plan);
    }

    @Transactional
    public FollowUpReportDTO evaluateDailyCheckIn(CheckInDTO dto) {
        RecoveryPlan plan = planRepository.findById(dto.getRecoveryPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Target recovery context dropped."));

        DailyCheckIn checkIn = DailyCheckIn.builder()
                .recoveryPlanId(plan.getId())
                .painScale(dto.getPainScale())
                .hasFever(dto.isHasFever())
                .patientNotes(dto.getPatientNotes())
                .build();

        String status;
        String advisoryMessage;

        // Structured Clinical Logic Branching for Post-Op Deviations
        if (dto.isHasFever() && dto.getPainScale() >= 7) {
            status = "FLAG_PHYSICIAN";
            plan.setMonitoringStatus("COMPLICATED");
            advisoryMessage = "CRITICAL WARNING: Concurrent high pain score and fever indicate possible post-operative infection. Your clinical care team has been alerted. Please seek emergency guidance.";
        } else if (dto.getPainScale() >= 5 || dto.isHasFever()) {
            status = "CAUTION";
            advisoryMessage = "CAUTION INDICATED: Pain levels are elevated above basic baselines. Rest, stick to prescribed pain medicine delivery instructions, and monitor your temperature carefully.";
        } else {
            status = "ON_TRACK";
            advisoryMessage = "RECOVERY ON TRACK: Metrics fall into normal healing parameters. Maintain your rehabilitation schedule.";
        }

        checkIn.setAssessmentFlag(status);
        checkInRepository.save(checkIn);
        planRepository.save(plan);

        return FollowUpReportDTO.builder()
                .checkInId(checkIn.getId())
                .statusFlag(checkIn.getAssessmentFlag())
                .clinicalGuidance(advisoryMessage)
                .checkedAt(checkIn.getSubmittedAt())
                .build();
    }
}
