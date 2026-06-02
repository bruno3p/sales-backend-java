package com.example.demo.controller;

import com.example.demo.model.MedicalReport;
import com.example.demo.service.MedicalReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class MedicalReportController {

	@Autowired
	private MedicalReportService reportService;

	@GetMapping
	public List<MedicalReport> getAll() {
		return reportService.findAll();
	}

	@PostMapping
	public ResponseEntity<MedicalReport> create(@Valid @RequestBody MedicalReport report) {
		reportService.createReport(report);
		return ResponseEntity.ok(report);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicalReport> getById(@PathVariable Long id) {
		MedicalReport report = reportService.findById(id);
		if (report != null) {
			return ResponseEntity.ok(report);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedicalReport> update(@PathVariable Long id, @Valid @RequestBody MedicalReport report) {
		report.setId(id);
		reportService.updateReport(report);
		return ResponseEntity.ok(report);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		reportService.deleteReport(id);
		return ResponseEntity.ok("Laudo médico deletado com sucesso!");
	}

	@GetMapping("/search")
	public List<MedicalReport> getByName(@RequestParam String name) {
		return reportService.findByNameContaining(name);
	}

	@GetMapping("/doctor/{doctorId}")
	public List<MedicalReport> getByDoctorId(@PathVariable Long doctorId) {
		return reportService.findByDoctorId(doctorId);
	}

	@GetMapping("/patient/{patientId}")
	public List<MedicalReport> getByPatientId(@PathVariable Long patientId) {
		return reportService.findByPatientId(patientId);
	}

	@PostMapping("/{id}/upload")
	public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
		try {
			reportService.processUploadedReport(id, file);
			MedicalReport updatedReport = reportService.findById(id);
			return ResponseEntity.ok(updatedReport);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
					.body(java.util.Collections.singletonMap("error", "Erro ao processar arquivo: " + e.getMessage()));
		}
	}

	@GetMapping("/{id}/download")
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long id) {
		MedicalReport report = reportService.findById(id);
		if (report == null || report.getOriginalFileName() == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			java.nio.file.Path file = java.nio.file.Paths.get("uploads/").resolve(report.getOriginalFileName());
			org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return ResponseEntity.ok()
						.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getOriginalFileName() + "\"")
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
