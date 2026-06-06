package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.repository;

import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model.ErBedTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ErBedTrackerRepository extends JpaRepository<ErBedTracker, Long> {
    List<ErBedTracker> findByIsOccupiedFalse();
}