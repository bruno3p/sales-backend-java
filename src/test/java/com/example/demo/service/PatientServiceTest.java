package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(101L);
        patient.setName("Paciente Teste");
        patient.setEmail("paciente@email.com");
        patient.setPassword("Senha@123");
    }

    @Test
    void testFindAll() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient));

        List<Patient> patients = patientService.findAll();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("Paciente Teste", patients.get(0).getName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(patientRepository.findById(101L)).thenReturn(patient);

        Patient found = patientService.findById(101L);

        assertNotNull(found);
        assertEquals("Paciente Teste", found.getName());
        verify(patientRepository, times(1)).findById(101L);
    }

    @Test
    void testCreatePatient_Success() {
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(patient.getPassword())).thenReturn("hashedPassword");

        patientService.createPatient(patient);

        assertEquals("hashedPassword", patient.getPassword());
        verify(patientRepository, times(1)).save(patient);
        verify(passwordEncoder, times(1)).encode("Senha@123");
    }

    @Test
    void testCreatePatient_EmailAlreadyExists() {
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(patient);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            patientService.createPatient(patient);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("E-mail já cadastrado", exception.getReason());
        verify(patientRepository, never()).save(any(Patient.class));
    }
}
