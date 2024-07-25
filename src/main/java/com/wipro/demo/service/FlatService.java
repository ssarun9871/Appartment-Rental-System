package com.wipro.demo.service;

import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlatService {

	private final FlatRepository flatRepository;
	private final LandlordRepository landlordRepository;


	public FlatService(FlatRepository flatRepository, LandlordRepository landlordRepository) {
		this.flatRepository = flatRepository;
		this.landlordRepository = landlordRepository;
	}

	public List<Flat> getAllFlats() {
		return flatRepository.findAll();
	}

	public Optional<Flat> getFlatById(Integer id) {
		return flatRepository.findById(id);
	}

	public Flat addFlat(Flat flat, Integer landlord_id) {

		Landlord landlord = landlordRepository.findById(landlord_id)
				.orElseThrow(() -> new RuntimeException("Landlord not found"));

		flat.setLandlord(landlord);
		flat.setAvailability(true);

		return flatRepository.save(flat);
	}

	public void deleteFlat(Integer id) {
		flatRepository.deleteById(id);
	}
}
