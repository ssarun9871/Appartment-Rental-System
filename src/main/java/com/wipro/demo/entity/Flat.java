package com.wipro.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Flat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer flat_id;

	@NotNull(message = "Address cannot be null")
	@Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
	private String address;

	@NotNull(message = "Availability cannot be null")
	private Boolean availability;

	@NotNull(message = "Rent cannot be null")
	@Positive(message = "Rent must be a positive number")
	private Integer rent;

	@ManyToOne
	@JoinColumn(name = "landlord_id")
	private Landlord landlord;
	
    @OneToOne(mappedBy = "flat")
    private Tenant tenant;
    

    @OneToMany(mappedBy = "flat")
    private List<Booking> bookings;
}
