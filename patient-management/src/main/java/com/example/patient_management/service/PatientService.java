package com.example.patient_management.service;

import com.example.patient_management.model.Patient;
import com.example.patient_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.patient_management.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class PatientService implements PatientServiceInterface{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        if (patient != null) {
            patient.setFirstName(patientDetails.getFirstName());
            patient.setLastName(patientDetails.getLastName());
            patient.setEmail(patientDetails.getEmail());
            return patientRepository.save(patient);
        }
        return null;
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> searchPatients(String query) {
        return patientRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query);
    }
}

