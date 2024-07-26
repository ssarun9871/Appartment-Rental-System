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

	public Landlord addLandlord(Landlord landlord) {
		landlord.setBlocked(false);
		landlord.setStatus("PENDING");
		landlord.setStatus("PENDING");
		landlord.setBlocked(true);
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
