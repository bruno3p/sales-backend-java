package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping
    public List<Appointment> getAll() {
        return service.findAll();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctorId(@PathVariable Long doctorId) {
        return service.findByDoctorId(doctorId);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody Appointment appointment) {
        service.create(appointment);
        return ResponseEntity.ok("Agendamento criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        appointment.setId(id);
        service.update(appointment);
        return ResponseEntity.ok("Agendamento atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Agendamento deletado com sucesso!");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        try {
            service.cancel(id);
            return ResponseEntity.ok("Agendamento cancelado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
