package com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.repository;

import com.example.SpringWebJPA.Doctor.DoctorPrescriptionManagement.model.DigitalPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DigitalPrescriptionRepository extends JpaRepository<DigitalPrescription, Long> {
    List<DigitalPrescription> findByPatientId(Long patientId);
    List<DigitalPrescription> findByDoctorId(Long doctorId);
}