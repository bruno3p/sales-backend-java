package com.example.demo.controller;

import com.example.demo.model.DoctorSettings;
import com.example.demo.service.DoctorSettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor-settings")
public class DoctorSettingsController {

    @Autowired
    private DoctorSettingsService service;

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorSettings> getByDoctorId(@PathVariable Long doctorId) {
        DoctorSettings settings = service.getByDoctorId(doctorId);
        if (settings != null) {
            return ResponseEntity.ok(settings);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdate(@Valid @RequestBody DoctorSettings settings) {
        service.saveOrUpdate(settings);
        return ResponseEntity.ok("Configurações do médico salvas com sucesso!");
    }
}
