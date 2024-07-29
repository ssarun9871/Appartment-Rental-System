package com.wipro.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.response.ResponseHandler;

@Service
public class LandlordService {

	@Autowired
	private LandlordRepository landlordRepository;

	@Autowired
	private FlatService flatService;
	
	@Autowired
	private BookingService bookingService;

	public Landlord addLandlord(Landlord landlord) {
		landlord.setBlocked(false);
		landlord.setStatus("PENDING");
		return landlordRepository.save(landlord);
	}

	public ResponseEntity<Object> login(String username, String password) {
		Landlord landlord = landlordRepository.findByUsername(username);
		if (landlord == null) {
			return ResponseHandler.responseBuilder("No account found", HttpStatus.UNAUTHORIZED, null);
		}

		if (!landlord.getPassword().equals(password)) {
			return ResponseHandler.responseBuilder("Incorrect password provided", HttpStatus.UNAUTHORIZED, null);
		}
		if (!"APPROVED".equals(landlord.getStatus())) {
			return ResponseHandler.responseBuilder("Your account has not been approved yet", HttpStatus.FORBIDDEN,
					null);
		}
		if (landlord.getBlocked()) {
			return ResponseHandler.responseBuilder("Your account is blocked", HttpStatus.FORBIDDEN, null);
		}
		return ResponseHandler.responseBuilder("Success", HttpStatus.OK, landlord);
	}

	public List<Landlord> getAllLandlord() {
		return landlordRepository.findAll();
	}

	public ResponseEntity<Object> getFlatByLandlord(Integer landlord_id) {
		return flatService.getFlatByLandlord(landlord_id);
	}
	
	public ResponseEntity<Object> getBookingByLandlord(Integer landlord_id) {
		return bookingService.getBookingByFlat(landlord_id);
	}
	
	public String confirmBooking(Integer booking_id) {
		return bookingService.confirmBookings(booking_id);
	}
	
	public String rejectBooking(Integer booking_id) {
		return bookingService.rejectBookings(booking_id);
	}


	public String blockLandlord(Integer id) {
		Optional<Landlord> landlord = landlordRepository.findById(id);

		if (landlord.isPresent()) {
			Landlord l = landlord.get();
			l.setBlocked(true);
			landlordRepository.save(l);
			return "Landlord blocked successfully";
		} else {
			return "Landlord not found";
		}
	}

	public String deleteLandlord(Integer id) {
		if (landlordRepository.existsById(id)) {
			landlordRepository.deleteById(id);
			return "Landlord deleted successfully";
		} else {
			return "Landlord not found";
		}
	}
}
