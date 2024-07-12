package com.example.patient_management.service;

import com.example.patient_management.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentServiceInterface {

    public List<Appointment> getAllAppointments();
    public Appointment getAppointmentById(Long id);
    public Appointment createAppointmentByEmail(String email, LocalDateTime appointmentTime);
    public Appointment updateAppointment(Long id, Long patientId, LocalDateTime appointmentTime);
    public void deleteAppointment(Long id);
    public List<Appointment> filterAppointments(LocalDate date, LocalTime time);
    public List<Appointment> getAppointmentsByDateTime(LocalDate date, LocalTime time);
    public List<Appointment> getAppointmentsByDate(LocalDate date);
}
