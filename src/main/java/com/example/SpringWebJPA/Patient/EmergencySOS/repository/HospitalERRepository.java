package com.example.SpringWebJPA.Patient.EmergencySOS.repository;

import com.example.SpringWebJPA.Patient.EmergencySOS.model.HospitalER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalERRepository extends JpaRepository<HospitalER, Long> {
}