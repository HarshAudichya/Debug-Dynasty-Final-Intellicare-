package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.RecoveryPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RecoveryPlanRepository extends JpaRepository<RecoveryPlan, Long> {
    Optional<RecoveryPlan> findFirstByPatientIdOrderByInitializedAtDesc(Long patientId);
}