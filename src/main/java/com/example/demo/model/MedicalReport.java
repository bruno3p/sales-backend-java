package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalReport {
	private Long id;

	@NotBlank(message = "O nome/título é obrigatório")
	private String name;

	@NotBlank(message = "Os detalhes do laudo são obrigatórios")
	private String details;

	@NotNull(message = "O ID do paciente é obrigatório")
	@com.fasterxml.jackson.annotation.JsonProperty("patient_id")
	private Long patientId;

	@com.fasterxml.jackson.annotation.JsonProperty("doctor_id")
	private Long doctorId; // Null quando enviado pelo paciente

	private LocalDate date;
	
	private Boolean isAiSummarized;
	
	private String aiPointsOfAttention;
	
	private String originalFileName;
}
