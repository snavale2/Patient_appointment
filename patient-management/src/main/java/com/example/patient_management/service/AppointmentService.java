package com.example.patient_management.service;

import com.example.patient_management.dto.AppointmentDTO;
import com.example.patient_management.dto.PatientDTO;
import com.example.patient_management.exception.ResourceNotFoundException;
import com.example.patient_management.model.Appointment;
import com.example.patient_management.model.Patient;
import com.example.patient_management.repository.AppointmentRepository;
import com.example.patient_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements AppointmentServiceInterface{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    private AppointmentDTO convertToDTO(Appointment appointment) {
        PatientDTO patientDTO = new PatientDTO(
                appointment.getPatient().getId(),
                appointment.getPatient().getFirstName(),
                appointment.getPatient().getLastName(),
                appointment.getPatient().getEmail()
        );
        return new AppointmentDTO(
                appointment.getId(),
                patientDTO,
                appointment.getAppointmentTime()
        );
    }

    private Appointment convertToEntity(AppointmentDTO appointmentDTO) {
        Patient patient = patientRepository.findById(appointmentDTO.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentDTO.getPatient().getId()));


        Patient simplifiedPatient = new Patient();
        simplifiedPatient.setId(patient.getId());
        simplifiedPatient.setFirstName(patient.getFirstName());
        simplifiedPatient.setLastName(patient.getLastName());
        simplifiedPatient.setEmail(patient.getEmail());

        return new Appointment(
                appointmentDTO.getId(),
                simplifiedPatient,  // Use simplified patient without List<Appointment>
                appointmentDTO.getAppointmentTime()
        );
    }


    @Override
    public List<AppointmentDTO> getAllAppointments() {
        // Fetch all appointments and convert to DTOs
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return convertToDTO(appointment);
    }


    @Override
    public AppointmentDTO createAppointmentByEmail(String email, LocalDateTime appointmentTime) {
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient not found with email " + email);
        }

        // Check if the time slot is already booked
        List<Appointment> existingAppointments = appointmentRepository.findByAppointmentTimeBetween(
                appointmentTime,
                appointmentTime.plusHours(1)
        );

        if (!existingAppointments.isEmpty()) {
            throw new IllegalStateException("Time slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentTime);

        return convertToDTO(appointmentRepository.save(appointment));
    }


    @Override
    public AppointmentDTO updateAppointment(Long id, LocalDateTime appointmentTime) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointment.setAppointmentTime(appointmentTime);
        return convertToDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentDTO> filterAppointments(LocalDate date, LocalTime time) {
        if (date != null && time != null) {
            LocalDateTime startDateTime = LocalDateTime.of(date, time);
            LocalDateTime endDateTime = startDateTime.plusHours(1);
            return appointmentRepository.findByAppointmentTimeBetween(startDateTime, endDateTime)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (date != null) {
            LocalDateTime startDateTime = date.atStartOfDay();
            LocalDateTime endDateTime = date.plusDays(1).atStartOfDay();
            return appointmentRepository.findByAppointmentTimeBetween(startDateTime, endDateTime)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (time != null) {
            LocalDateTime startDateTime = LocalDateTime.now().with(time);
            LocalDateTime endDateTime = startDateTime.plusHours(1);
            return appointmentRepository.findByAppointmentTimeBetween(startDateTime, endDateTime)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return appointmentRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<AppointmentDTO> getAppointmentsByDateTime(LocalDate date, LocalTime time) {
        return appointmentRepository.findByAppointmentTime(LocalDateTime.of(date, time)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentTimeBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
