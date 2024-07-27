package com.wipro.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {

	@NotNull
	private LocalDate from_date;

	@NotNull
	private LocalDate to_date;

	@NotNull
	private Integer flat_id;

	@NotNull
	private Integer tenant_id;

	public BookingRequestDTO() {
	}

	public BookingRequestDTO(@NotNull LocalDate from_date, @NotNull LocalDate to_date, @NotNull Integer flat_id,
			@NotNull Integer tenant_id) {
		super();
		this.from_date = from_date;
		this.to_date = to_date;
		this.flat_id = flat_id;
		this.tenant_id = tenant_id;
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

	public Integer getFlat_id() {
		return flat_id;
	}

	public void setFlat_id(Integer flat_id) {
		this.flat_id = flat_id;
	}

	public Integer getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(Integer tenant_id) {
		this.tenant_id = tenant_id;
	}

}
