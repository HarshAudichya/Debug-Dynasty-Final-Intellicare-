package com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.repository;
import com.example.SpringWebJPA.Doctor.DoctorAvailabiltySchedulingTriagePriorityBooking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
