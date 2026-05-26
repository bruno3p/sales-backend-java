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
	public ResponseEntity<String> create(@Valid @RequestBody MedicalReport report) {
		reportService.createReport(report);
		return ResponseEntity.ok("Laudo médico criado com sucesso!");
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
	public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody MedicalReport report) {
		report.setId(id);
		reportService.updateReport(report);
		return ResponseEntity.ok("Laudo médico atualizado com sucesso!");
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

	@GetMapping("/category/{categoryName}")
	public List<MedicalReport> getByCategory(@PathVariable String categoryName) {
		return reportService.findByCategoryName(categoryName);
	}
}
