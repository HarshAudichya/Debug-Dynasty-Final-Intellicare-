package com.example.SpringWebJPA.Patient.BloodRequest.repository;

import com.example.SpringWebJPA.Patient.BloodRequest.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    Optional<BloodRequest> findFirstByPatientIdOrderByCreatedAtDesc(Long patientId);
}
