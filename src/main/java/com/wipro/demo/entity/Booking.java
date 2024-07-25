package com.wipro.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer booking_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @NotNull
    private LocalDate from_date;

    @NotNull
    private LocalDate to_date;
    
    @NotNull
    private String booking_status;

	public Booking() {}

	public Booking(Integer booking_id, @NotNull Flat flat, @NotNull Tenant tenant, @NotNull LocalDate from_date,
			@NotNull LocalDate to_date, @NotNull String booking_status) {
		super();
		this.booking_id = booking_id;
		this.flat = flat;
		this.tenant = tenant;
		this.from_date = from_date;
		this.to_date = to_date;
		this.booking_status = booking_status;
	}

	public Integer getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}

	public Flat getFlat() {
		return flat;
	}

	public void setFlat(Flat flat) {
		this.flat = flat;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public LocalDate getFrom_date() {
		return from_date;
	}

	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}

	public LocalDate getTo_date() {
		return to_date;
	}

	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}

	public String getBooking_status() {
		return booking_status;
	}

	public void setBooking_status(String booking_status) {
		this.booking_status = booking_status;
	}
    
    

}
