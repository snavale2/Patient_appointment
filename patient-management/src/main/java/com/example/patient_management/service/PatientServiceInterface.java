package com.example.patient_management.service;

import com.example.patient_management.dto.PatientDTO;
import com.example.patient_management.model.Patient;

import java.util.List;

public interface PatientServiceInterface {

    public List<PatientDTO> getAllPatients();
    public PatientDTO getPatientById(Long id);
    public PatientDTO createPatient(PatientDTO patientDTO);
    public PatientDTO updatePatient(Long id, PatientDTO patientDetails);
    public void deletePatient(Long id);
    public List<PatientDTO> searchPatients(String query);
}
