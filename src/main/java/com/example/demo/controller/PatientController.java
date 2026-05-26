package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;

	@GetMapping
	public List<Patient> getAll() {
		return patientService.findAll();
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody Patient patient) {
		patientService.createPatient(patient);
		return ResponseEntity.ok("Paciente criado com sucesso!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Patient patient) {
		patient.setId(id);
		patientService.updatePatient(patient);
		return ResponseEntity.ok("Paciente atualizado com sucesso!");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok("Paciente deletado com sucesso!");
	}
}
