package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.repository;

import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model.AmbulanceTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AmbulanceTrackerRepository extends JpaRepository<AmbulanceTracker, Long> {
    List<AmbulanceTracker> findByOperationalStatus(String status);
}