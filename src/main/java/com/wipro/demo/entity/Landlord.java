package com.wipro.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Landlord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer landlord_id;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String first_name;

	@NotNull
	private String last_name;

	@NotNull
	@Pattern(regexp = "\\d{10}", message = "Mobile number should be 10 digits")
	private String mobile;

	@NotNull
	@Min(value = 18, message = "Age should be >= 18")
	private Integer age;

	@NotNull
	private String status;

	@NotNull
	private Boolean blocked;
	
	@OneToMany(mappedBy = "landlord")
	@JsonIgnore
	private List<Flat> flats;

	public Landlord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Landlord(Integer landlord_id, @NotNull String username, @NotNull String password, @NotNull String first_name,
			@NotNull String last_name,
			@NotNull @Pattern(regexp = "\\d{10}", message = "Mobile number should be 10 digits") String mobile,
			@NotNull @Min(value = 18, message = "Age should be >= 18") Integer age, @NotNull String status,
			@NotNull Boolean blocked, List<Flat> flats) {
		super();
		this.landlord_id = landlord_id;
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.mobile = mobile;
		this.age = age;
		this.status = status;
		this.blocked = blocked;
		this.flats = flats;
	}

	public Integer getLandlord_id() {
		return landlord_id;
	}

	public void setLandlord_id(Integer landlord_id) {
		this.landlord_id = landlord_id;
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public List<Flat> getFlats() {
		return flats;
	}

	public void setFlats(List<Flat> flats) {
		this.flats = flats;
	}
	
	

}
