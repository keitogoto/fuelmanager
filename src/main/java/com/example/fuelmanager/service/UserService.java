package com.example.fuelmanager.service;

import org.springframework.stereotype.Service;

import com.example.fuelmanager.entity.User;
import com.example.fuelmanager.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new RuntimeException("User not found: " + username));
	}
}
