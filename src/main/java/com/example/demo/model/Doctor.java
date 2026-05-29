package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Doctor {
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	private String name;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	private String password;

	@NotBlank(message = "A especialidade é obrigatória")
	private String specialty;

	private String avatar;

	public Doctor() {}

	public Doctor(Long id, String name, String email, String password, String specialty, String avatar) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.specialty = specialty;
		this.avatar = avatar;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getSpecialty() { return specialty; }
	public void setSpecialty(String specialty) { this.specialty = specialty; }
	public String getAvatar() { return avatar; }
	public void setAvatar(String avatar) { this.avatar = avatar; }
}
