package com.example.patient_management.service;

import com.example.patient_management.dto.PatientDTO;
import com.example.patient_management.model.Patient;
import com.example.patient_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.patient_management.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements PatientServiceInterface{

    @Autowired
    private PatientRepository patientRepository;

    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail()
        );
    }

    private Patient convertToEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setEmail(patientDTO.getEmail());
        return patient;
    }


    @Override
    public List<PatientDTO> getAllPatients() {
        // Fetch all patients and convert to DTOs
        return patientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return convertToDTO(patient);
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = convertToEntity(patientDTO);
        return convertToDTO(patientRepository.save(patient));
    }


    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDetails) {
        // Fetch the existing patient entity
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        // Update entity fields and save
        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setEmail(patientDetails.getEmail());
        return convertToDTO(patientRepository.save(patient));
    }


    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.deleteById(id);
    }


    @Override
    public List<PatientDTO> searchPatients(String query) {
        // Search patients and convert to DTOs
        return patientRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

