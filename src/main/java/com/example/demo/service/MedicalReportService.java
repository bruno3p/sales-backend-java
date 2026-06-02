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

	public List<MedicalReport> findByDoctorId(Long doctorId) {
		return reportRepository.findByDoctorId(doctorId);
	}

	public List<MedicalReport> findByPatientId(Long patientId) {
		return reportRepository.findByPatientId(patientId);
	}

	@Autowired
	private AiService aiService;

	public void processUploadedReport(Long reportId, org.springframework.web.multipart.MultipartFile file) throws Exception {
		MedicalReport report = findById(reportId);
		if (report == null) {
			throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Laudo não encontrado");
		}

		java.nio.file.Path uploadPath = java.nio.file.Paths.get("uploads/");
		if (!java.nio.file.Files.exists(uploadPath)) {
			java.nio.file.Files.createDirectories(uploadPath);
		}

		String originalFilename = file.getOriginalFilename();
		String fileExtension = "";
		if (originalFilename != null && originalFilename.contains(".")) {
			fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
		}
		String newFilename = java.util.UUID.randomUUID().toString() + fileExtension;
		java.nio.file.Path filePath = uploadPath.resolve(newFilename);
		
		try (java.io.InputStream inputStream = file.getInputStream()) {
			java.nio.file.Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		}

		report.setOriginalFileName(newFilename);
		report.setIsAiSummarized(false);
		reportRepository.update(report);
		
		if (".pdf".equalsIgnoreCase(fileExtension)) {
			try (org.apache.pdfbox.pdmodel.PDDocument document = org.apache.pdfbox.Loader.loadPDF(filePath.toFile())) {
				org.apache.pdfbox.text.PDFTextStripper pdfStripper = new org.apache.pdfbox.text.PDFTextStripper();
				String text = pdfStripper.getText(document);
				
				if (text != null && !text.trim().isEmpty()) {
					String aiSummary = aiService.summarizeMedicalReport(text);
					report.setAiPointsOfAttention(aiSummary);
					report.setIsAiSummarized(true);
					reportRepository.update(report);
				}
			}
		}
	}
}
