package com.wipro.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Flat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull(message = "Address cannot be null")
	@Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
	private String address;

	@NotNull(message = "Availability cannot be null")
	private Boolean availability;

	@NotNull(message = "Cost cannot be null")
	@Positive(message = "Cost must be a positive number")
	private Integer cost;

	@NotNull(message = "Owner cannot be null")
	@ManyToOne
	@JoinColumn(name = "ownerId")
	private User owner;

	public Flat() {
	}

	public Flat(Integer id,
			@NotNull(message = "Address cannot be null") @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters") String address,
			@NotNull(message = "Availability cannot be null") Boolean availability,
			@NotNull(message = "Cost cannot be null") @Positive(message = "Cost must be a positive number") Integer cost,
			@NotNull(message = "Owner cannot be null") User owner) {
		super();
		this.id = id;
		this.address = address;
		this.availability = availability;
		this.cost = cost;
		this.owner = owner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
