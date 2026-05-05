package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("E-mail já cadastrado!");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.update(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}