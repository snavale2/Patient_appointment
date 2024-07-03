package com.example.patient_management.repository;

import com.example.patient_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email);
}
