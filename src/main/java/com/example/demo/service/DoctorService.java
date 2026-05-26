package com.example.demo.service;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;

	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}

	public void createDoctor(Doctor doctor) {
		Doctor existing = doctorRepository.findByEmail(doctor.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
		}
		doctorRepository.save(doctor);
	}

	public void updateDoctor(Doctor doctor) {
		doctorRepository.update(doctor);
	}

	public void deleteDoctor(Long id) {
		doctorRepository.deleteById(id);
	}
}
