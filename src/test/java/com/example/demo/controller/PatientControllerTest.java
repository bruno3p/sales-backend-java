package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(101L);
        patient.setName("Paciente Teste");
        patient.setEmail("paciente@email.com");
        patient.setPassword("SenhaForte@123");
    }

    @Test
    void testGetAllPatients() {
        when(patientService.findAll()).thenReturn(Arrays.asList(patient));

        List<Patient> patients = patientController.getAll();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("Paciente Teste", patients.get(0).getName());
    }

    @Test
    void testGetPatientById_Found() {
        when(patientService.findById(101L)).thenReturn(patient);

        ResponseEntity<Patient> response = patientController.getById(101L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Paciente Teste", response.getBody().getName());
    }

    @Test
    void testGetPatientById_NotFound() {
        when(patientService.findById(102L)).thenReturn(null);

        ResponseEntity<Patient> response = patientController.getById(102L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreatePatient() {
        Mockito.doNothing().when(patientService).createPatient(any(Patient.class));

        ResponseEntity<String> response = patientController.create(patient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paciente criado com sucesso!", response.getBody());
    }
}
