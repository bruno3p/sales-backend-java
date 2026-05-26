package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalReport {
	private Long id;

	@NotBlank(message = "O nome/título é obrigatório")
	private String name;

	@NotBlank(message = "Os detalhes do laudo são obrigatórios")
	private String details;

	@NotNull(message = "A categoria do laudo é obrigatória")
	private Long categoryId;

	@NotNull(message = "O ID do paciente é obrigatório")
	private Long patientId;

	@NotNull(message = "O ID do médico é obrigatório")
	private Long doctorId;
}
