package com.example.patient_management.controller;

import com.example.patient_management.model.Appointment;
import com.example.patient_management.model.Patient;
import com.example.patient_management.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    public void testGetAppointments() throws Exception {
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com");
        Appointment appointment1 = new Appointment(1L, patient, LocalDateTime.now().plusDays(1));
        Appointment appointment2 = new Appointment(2L, patient, LocalDateTime.now().plusDays(2));

        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);

        Mockito.when(appointmentService.getAllAppointments()).thenReturn(appointments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/appointments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].patient.id").value(1L))
                .andExpect(jsonPath("$[0].patient.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].patient.id").value(1L))
                .andExpect(jsonPath("$[1].patient.email").value("john.doe@example.com"));
    }

    @Test
    public void testCreateAppointment() throws Exception {
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com");
        Appointment appointment = new Appointment(1L, patient, LocalDateTime.now().plusDays(1));

        Mockito.when(appointmentService.createAppointmentByEmail(Mockito.anyString(), Mockito.any(LocalDateTime.class)))
                .thenReturn(appointment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointments")
                        .param("email", "john.doe@example.com")
                        .param("appointmentTime", "2024-07-10T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.patient.id").value(1L))
                .andExpect(jsonPath("$.patient.email").value("john.doe@example.com"));
    }
}
