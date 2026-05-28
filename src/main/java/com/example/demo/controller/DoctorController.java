package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;

	@GetMapping
	public List<Doctor> getAll() {
		return doctorService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getById(@PathVariable Long id) {
		Doctor doctor = doctorService.findById(id);
		if (doctor != null) {
			return ResponseEntity.ok(doctor);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody Doctor doctor) {
		doctorService.createDoctor(doctor);
		return ResponseEntity.ok("Médico criado com sucesso!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
		doctor.setId(id);
		doctorService.updateDoctor(doctor);
		return ResponseEntity.ok("Médico atualizado com sucesso!");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		doctorService.deleteDoctor(id);
		return ResponseEntity.ok("Médico deletado com sucesso!");
	}
}
