package com.example.demo.service;

import com.example.demo.model.MedicalReport;
import com.example.demo.repository.MedicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalReportService {
	@Autowired
	private MedicalReportRepository reportRepository;

	public List<MedicalReport> findAll() {
		return reportRepository.findAll();
	}

	public void createReport(MedicalReport report) {
		reportRepository.save(report);
	}

	public MedicalReport findById(Long id) {
		return reportRepository.findById(id);
	}

	public void updateReport(MedicalReport report) {
		reportRepository.update(report);
	}

	public void deleteReport(Long id) {
		reportRepository.delete(id);
	}

	public List<MedicalReport> findByNameContaining(String name) {
		return reportRepository.findByNameContaining(name);
	}

	public List<MedicalReport> findByCategoryName(String categoryName) {
		return reportRepository.findByCategoryName(categoryName);
	}

	public List<MedicalReport> findByDoctorId(Long doctorId) {
		return reportRepository.findByDoctorId(doctorId);
	}

	public List<MedicalReport> findByPatientId(Long patientId) {
		return reportRepository.findByPatientId(patientId);
	}
}
