package com.wipro.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.BookingRepository;
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;
import com.wipro.demo.response.ResponseHandler;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	FlatRepository flatRepository;

	@Autowired
	LandlordRepository landlordRepository;

	@Autowired
	TenantRepository tenantRepository;

	public ResponseEntity<Object> addBooking(BookingRequestDTO bookingRequestDTO) {
		Optional<Flat> flatOptional = flatRepository.findById(bookingRequestDTO.getFlat_id());
		Optional<Tenant> tenantOptional = tenantRepository.findById(bookingRequestDTO.getTenant_id());

		if (flatOptional.isPresent() && flatOptional.get().getAvailability() && tenantOptional.isPresent()) {
			Flat flat = flatOptional.get();
			Tenant tenant = tenantOptional.get();

			flat.setAvailability(false);
			flatRepository.save(flat);

			Booking booking = new Booking();
			booking.setFlat(flat);
			booking.setTenant(tenant);
			booking.setFrom_date(bookingRequestDTO.getFrom_date());
			booking.setTo_date(bookingRequestDTO.getTo_date());
			booking.setBooking_status("PENDING");

			Booking data = bookingRepository.save(booking);
			return ResponseHandler.responseBuilder("Success", HttpStatus.OK, data);
		} else {
			return ResponseHandler.responseBuilder("Flat not found, not available, or Tenant not found",
					HttpStatus.NOT_FOUND, null);
		}
	}

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	public List<Booking> getBookingsByTenantId(Integer tenant_id) {
		Optional<Tenant> tenant = tenantRepository.findById(tenant_id);
		return bookingRepository.findByTenant(tenant);
	}

	public ResponseEntity<Object> getBookingByFlat(Integer flat_id) {
		Optional<Flat> flatOptional = flatRepository.findById(flat_id);

		if (flatOptional.isEmpty()) {
			return ResponseHandler.responseBuilder("Flat not found", HttpStatus.NOT_FOUND, null);
		} else {
			Flat flat = flatOptional.get();
			List<Booking> bookings = bookingRepository.findByFlat(flat);
			return ResponseHandler.responseBuilder("Success", HttpStatus.OK, bookings);
		}
	}

	public String cancelBookings(Integer id) {
		Optional<Booking> booking = bookingRepository.findById(id);

		if (booking.isPresent()) {
			Booking b = booking.get();
			b.setBooking_status("CANCELLED");
			bookingRepository.save(b);
			return "Booking cancelled successfully";
		} else {
			return "Booking not found";
		}

	}

	public String confirmBookings(Integer id) {
		Optional<Booking> booking = bookingRepository.findById(id);

		if (booking.isPresent()) {
			Booking b = booking.get();
			b.setBooking_status("CONFIRMED");

			bookingRepository.save(b);
			return "Booking confirmed successfully";
		} else {
			return "Booking not found";
		}

	}

	public String rejectBookings(Integer id) {
		Optional<Booking> booking = bookingRepository.findById(id);

		if (booking.isPresent()) {
			Booking b = booking.get();
			b.setBooking_status("REJECTED");
			
			Flat flat = b.getFlat();
			flat.setAvailability(true);
			
			bookingRepository.save(b);
			return "Booking rejected successfully";
		} else {
			return "Booking not found";
		}

	}

}
