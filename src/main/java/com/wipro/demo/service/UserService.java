package com.wipro.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wipro.demo.entity.User;
import com.wipro.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User signup(User user) {	

		user.setStatus("pending"); 

		return userRepository.save(user);
	}

	public User login(String username, String password) {

		User user = userRepository.findByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new RuntimeException("Invalid credentials");
		}
	}
}
