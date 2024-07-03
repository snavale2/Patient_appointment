package com.example.patient_management.service;

import com.example.patient_management.model.Patient;

import java.util.List;

public interface PatientServiceInterface {

    public List<Patient> getAllPatients();
    public Patient getPatientById(Long id);
    public Patient createPatient(Patient patient);
    public Patient updatePatient(Long id, Patient patientDetails);
    public void deletePatient(Long id);
    public List<Patient> searchPatients(String query);
}
