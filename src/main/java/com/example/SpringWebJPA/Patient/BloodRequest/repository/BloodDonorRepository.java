package com.example.SpringWebJPA.Patient.BloodRequest.repository;
import com.example.SpringWebJPA.Patient.BloodRequest.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {
    @Query("SELECT d FROM BloodDonor d WHERE d.bloodType = :bloodType AND d.isAvailable = true")
    List<BloodDonor> findCompatibleDonors(@Param("bloodType") String bloodType);
}
