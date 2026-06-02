package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
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
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Test");
        doctor.setEmail("test@clinic.com");
        doctor.setPassword("Password@123");
        doctor.setSpecialty("Cardiologia");
    }

    @Test
    void testFindAll() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));

        List<Doctor> doctors = doctorService.findAll();

        assertNotNull(doctors);
        assertEquals(1, doctors.size());
        assertEquals("Dr. Test", doctors.get(0).getName());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(doctorRepository.findById(1L)).thenReturn(doctor);

        Doctor found = doctorService.findById(1L);

        assertNotNull(found);
        assertEquals("Dr. Test", found.getName());
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateDoctor_Success() {
        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(doctor.getPassword())).thenReturn("hashedPassword");

        doctorService.createDoctor(doctor);

        assertEquals("hashedPassword", doctor.getPassword());
        verify(doctorRepository, times(1)).save(doctor);
        verify(passwordEncoder, times(1)).encode("Password@123");
    }

    @Test
    void testCreateDoctor_EmailAlreadyExists() {
        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(doctor);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            doctorService.createDoctor(doctor);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("E-mail já cadastrado", exception.getReason());
        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}
