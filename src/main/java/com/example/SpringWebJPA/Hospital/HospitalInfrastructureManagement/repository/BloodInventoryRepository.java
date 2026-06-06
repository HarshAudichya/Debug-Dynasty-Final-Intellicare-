package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.repository;

import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    Optional<BloodInventory> findByBloodType(String bloodType);
}
