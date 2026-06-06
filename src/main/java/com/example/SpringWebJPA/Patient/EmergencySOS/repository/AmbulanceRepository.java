package com.example.SpringWebJPA.Patient.EmergencySOS.repository;

import com.example.SpringWebJPA.Patient.EmergencySOS.model.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    @Query("SELECT a FROM Ambulance a WHERE a.isAvailable = true")
    List<Ambulance> findAvailableAmbulances();
}