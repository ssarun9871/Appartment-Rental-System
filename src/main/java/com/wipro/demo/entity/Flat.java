package com.wipro.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "landlord_id")
	private Landlord landlord;

	@OneToOne(mappedBy = "flat")
	@JsonIgnore
	private Tenant tenant;

	@OneToMany(mappedBy = "flat")
	@JsonIgnore
	private List<Booking> bookings;

	public Flat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Flat(Integer flat_id,
			@NotNull(message = "Address cannot be null") @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters") String address,
			@NotNull(message = "Availability cannot be null") Boolean availability,
			@NotNull(message = "Rent cannot be null") @Positive(message = "Rent must be a positive number") Integer rent,
			Landlord landlord, Tenant tenant, List<Booking> bookings) {
		super();
		this.flat_id = flat_id;
		this.address = address;
		this.availability = availability;
		this.rent = rent;
		this.landlord = landlord;
		this.tenant = tenant;
		this.bookings = bookings;
	}

	public Integer getFlat_id() {
		return flat_id;
	}

	public void setFlat_id(Integer flat_id) {
		this.flat_id = flat_id;
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

	public Integer getRent() {
		return rent;
	}

	public void setRent(Integer rent) {
		this.rent = rent;
	}

	public Landlord getLandlord() {
		return landlord;
	}

	public void setLandlord(Landlord landlord) {
		this.landlord = landlord;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "Flat [flat_id=" + flat_id + ", address=" + address + ", availability=" + availability + ", rent=" + rent
				+ ", landlord=" + landlord + ", tenant=" + tenant + ", bookings=" + bookings + "]";
	}

	
}
