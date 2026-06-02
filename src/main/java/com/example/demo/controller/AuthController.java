package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        Doctor doctor = doctorRepository.findByEmail(loginDTO.getEmail());
        if (doctor != null && passwordEncoder.matches(loginDTO.getPassword(), doctor.getPassword())) {
            String token = jwtUtil.generateToken(doctor.getEmail(), doctor.getId(), "ROLE_DOCTOR");
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "DOCTOR");
            response.put("user", doctor);
            return ResponseEntity.ok(response);
        }

        Patient patient = patientRepository.findByEmail(loginDTO.getEmail());
        if (patient != null && passwordEncoder.matches(loginDTO.getPassword(), patient.getPassword())) {
            String token = jwtUtil.generateToken(patient.getEmail(), patient.getId(), "ROLE_PATIENT");
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "PATIENT");
            response.put("user", patient);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
