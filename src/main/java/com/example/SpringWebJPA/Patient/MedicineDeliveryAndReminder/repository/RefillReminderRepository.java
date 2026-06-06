package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository;

import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model.RefillReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RefillReminderRepository extends JpaRepository<RefillReminder, Long> {
    @Query("SELECT r FROM RefillReminder r WHERE r.expectedDepletionDate <= :targetDate AND r.isReminderSent = false")
    List<RefillReminder> findPendingReminders(LocalDate targetDate);
}