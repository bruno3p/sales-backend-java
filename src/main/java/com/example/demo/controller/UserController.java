package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAll() {
		return userService.findAll();
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.ok("Usuário criado com sucesso!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody User user) {
		user.setId(id);
		userService.updateUser(user);
		return ResponseEntity.ok("Usuário atualizado com sucesso!");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("Usuário deletado com sucesso!");
	}
}
