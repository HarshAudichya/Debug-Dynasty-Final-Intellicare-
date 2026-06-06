package com.example.SpringWebJPA.Patient.EmergencySOS.repository;

import com.example.SpringWebJPA.Patient.EmergencySOS.model.EmergencyIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyIncidentRepository extends JpaRepository<EmergencyIncident, Long> {
}