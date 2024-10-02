package com.example.patient_management.service;

import com.example.patient_management.dto.AppointmentDTO;
import com.example.patient_management.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentServiceInterface {

    public List<AppointmentDTO> getAllAppointments();
    public AppointmentDTO getAppointmentById(Long id);
    public AppointmentDTO createAppointmentByEmail(String email, LocalDateTime appointmentTime);
    public AppointmentDTO updateAppointment(Long id, LocalDateTime appointmentTime);
    public void deleteAppointment(Long id);
    public List<AppointmentDTO> filterAppointments(LocalDate date, LocalTime time);
    public List<AppointmentDTO> getAppointmentsByDateTime(LocalDate date, LocalTime time);
    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date);
}
