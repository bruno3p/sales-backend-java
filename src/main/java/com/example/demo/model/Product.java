package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	private String name;

	@NotNull(message = "O preço é obrigatório")
	@Positive(message = "O preço deve ser maior que zero")
	private Double price;

	@NotNull(message = "O estoque é obrigatório")
	@Min(value = 0, message = "O estoque não pode ser negativo")
	private Integer stock;

	@NotNull(message = "A categoria é obrigatória")
	private Long categoryId;

	@NotBlank(message = "Os detalhes do produto são obrigatórios")
	private String details;
}
