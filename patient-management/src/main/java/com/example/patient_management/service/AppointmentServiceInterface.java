package com.example.patient_management.service;

import com.example.patient_management.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentServiceInterface {

    public List<Appointment> getAllAppointments();
    public Appointment getAppointmentById(Long id);
    public Appointment createAppointment(Long patientId, LocalDateTime appointmentTime);
    public Appointment updateAppointment(Long id, Long patientId, LocalDateTime appointmentTime);
    public void deleteAppointment(Long id);
}
