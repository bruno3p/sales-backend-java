package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
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
public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Test");
        doctor.setEmail("test@clinic.com");
        doctor.setPassword("SenhaForte@123");
        doctor.setSpecialty("Cardiologia");
    }

    @Test
    void testGetAllDoctors() {
        when(doctorService.findAll()).thenReturn(Arrays.asList(doctor));

        List<Doctor> doctors = doctorController.getAll();

        assertNotNull(doctors);
        assertEquals(1, doctors.size());
        assertEquals("Dr. Test", doctors.get(0).getName());
    }

    @Test
    void testGetDoctorById_Found() {
        when(doctorService.findById(1L)).thenReturn(doctor);

        ResponseEntity<Doctor> response = doctorController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dr. Test", response.getBody().getName());
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorService.findById(2L)).thenReturn(null);

        ResponseEntity<Doctor> response = doctorController.getById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateDoctor() {
        Mockito.doNothing().when(doctorService).createDoctor(any(Doctor.class));

        ResponseEntity<String> response = doctorController.create(doctor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Médico criado com sucesso!", response.getBody());
    }
}
