package com.wipro.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 4, max = 20, message = "Username should be between 4 to 20 characters")
	@Column(unique = true)
	private String username;

	@NotNull
	@Size(min = 8, message = "Password should be at least 8 characters")
	private String password;

	@NotNull
	private String role;

	@NotNull
	private String status;

	public User() {}

	public User(Long id,
			@NotNull @Size(min = 4, max = 20, message = "Username should be between 4 to 20 characters") String username,
			@NotNull @Size(min = 8, message = "Password should be at least 8 characters") String password,
			@NotNull String role, @NotNull String status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", status="
				+ status + "]";
	}
	
	
}
