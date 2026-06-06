package com.example.SpringWebJPA.Patient.FollowUpAndRecovery.repository;
import com.example.SpringWebJPA.Patient.FollowUpAndRecovery.model.DailyCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyCheckInRepository extends JpaRepository<DailyCheckIn, Long> {
}