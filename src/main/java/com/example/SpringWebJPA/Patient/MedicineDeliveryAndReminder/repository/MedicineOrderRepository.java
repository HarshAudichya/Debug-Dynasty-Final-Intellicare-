package com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.repository;import com.example.SpringWebJPA.Patient.MedicineDeliveryAndReminder.model.MedicineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineOrderRepository extends JpaRepository<MedicineOrder, Long> {
}
