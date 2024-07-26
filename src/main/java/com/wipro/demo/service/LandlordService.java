package com.wipro.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.LandlordRepository;

@Service
public class LandlordService {

	@Autowired
	private LandlordRepository landlordRepository;

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
