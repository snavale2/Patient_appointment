package com.example.patient_management.controller;

import com.example.patient_management.model.Appointment;
import com.example.patient_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public Appointment createAppointment(@RequestParam String email, @RequestParam String appointmentTime) {
        LocalDateTime time = LocalDateTime.parse(appointmentTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return appointmentService.createAppointmentByEmail(email, time);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestParam Long patientId, @RequestParam String appointmentTime) {
        LocalDateTime time = LocalDateTime.parse(appointmentTime);
        return appointmentService.updateAppointment(id, patientId, time);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }


    @GetMapping
    public List<Appointment> getAppointments(@RequestParam(required = false) String date,
                                             @RequestParam(required = false) String time) {
        if (date != null && time != null) {
            return appointmentService.getAppointmentsByDateTime(LocalDate.parse(date), LocalTime.parse(time));
        } else if (date != null) {
            return appointmentService.getAppointmentsByDate(LocalDate.parse(date));
        } else {
            return appointmentService.getAllAppointments();
        }
    }
}
