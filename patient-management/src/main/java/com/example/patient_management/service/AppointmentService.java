package com.example.patient_management.service;

import com.example.patient_management.exception.ResourceNotFoundException;
import com.example.patient_management.model.Appointment;
import com.example.patient_management.model.Patient;
import com.example.patient_management.repository.AppointmentRepository;
import com.example.patient_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService implements AppointmentServiceInterface{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Override
    public Appointment createAppointment(Long patientId, LocalDateTime appointmentTime) {
        List<Appointment> existingAppointments = appointmentRepository.findByAppointmentTime(appointmentTime);
        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Appointment time is already booked.");
        }

        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("Patient not found.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentTime);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long id, Long patientId, LocalDateTime appointmentTime) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found.");
        }

        List<Appointment> existingAppointments = appointmentRepository.findByAppointmentTime(appointmentTime);
        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Appointment time is already booked.");
        }

        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new RuntimeException("Patient not found.");
        }

        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentTime);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
