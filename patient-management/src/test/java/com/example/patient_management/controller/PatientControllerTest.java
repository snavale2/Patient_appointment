package com.example.patient_management.controller;

import com.example.patient_management.model.Patient;
import com.example.patient_management.repository.PatientRepository;
import com.example.patient_management.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    public void testGetPatients() throws Exception {
        Patient patient1 = new Patient(1L, "John", "Doe", "john.doe@example.com");
        Patient patient2 = new Patient(2L, "Jane", "Doe", "jane.doe@example.com");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        Mockito.when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));
    }

    @Test
    public void testCreatePatient() throws Exception {
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com");

        Mockito.when(patientService.createPatient(Mockito.any(Patient.class)))
                .thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
}
