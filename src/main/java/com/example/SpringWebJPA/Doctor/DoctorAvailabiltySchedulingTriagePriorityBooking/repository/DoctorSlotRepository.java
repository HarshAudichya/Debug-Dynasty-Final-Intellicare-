package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.DoctorSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {

    @Query("SELECT s FROM DoctorSlot s WHERE s.doctorId = :doctorId AND s.isBooked = false ORDER BY s.slotStartTime ASC")
    List<DoctorSlot> findAvailableSlotsByDoctorSorted(@Param("doctorId") Long doctorId);
}
