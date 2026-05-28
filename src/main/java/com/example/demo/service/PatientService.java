package com.example.demo.service;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	public Patient findById(Long id) {
		return patientRepository.findById(id);
	}

	public void createPatient(Patient patient) {
		Patient existing = patientRepository.findByEmail(patient.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
		}
		patientRepository.save(patient);
	}

	public void updatePatient(Patient patient) {
		patientRepository.update(patient);
	}

	public void deletePatient(Long id) {
		patientRepository.deleteById(id);
	}
}
