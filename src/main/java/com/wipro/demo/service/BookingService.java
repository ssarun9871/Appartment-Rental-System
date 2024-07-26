package com.wipro.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.demo.entity.Booking;
import com.wipro.demo.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepository;

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
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

}
