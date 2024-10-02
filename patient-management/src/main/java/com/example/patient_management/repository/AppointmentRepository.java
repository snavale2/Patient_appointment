package com.example.patient_management.repository;

import com.example.patient_management.model.Appointment;
import com.example.patient_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentTime(LocalDateTime appointmentTime);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
