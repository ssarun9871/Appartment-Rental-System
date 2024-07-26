package com.wipro.demo.service;

import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.response.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> addFlat(Flat flat, Integer landlord_id) {
        Optional<Landlord> landlordOptional = landlordRepository.findById(landlord_id);
        
        if (landlordOptional.isPresent()) {
            Landlord landlord = landlordOptional.get();
            flat.setLandlord(landlord);
            flat.setAvailability(true);

            Flat data = flatRepository.save(flat);
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, data);
        } else {
            return ResponseHandler.responseBuilder("Landlord not found", HttpStatus.NOT_FOUND, null);
        }
    }

	public List<Flat> getAllFlats() {
		return flatRepository.findAll();
	}

	public Optional<Flat> getFlatById(Integer id) {
		return flatRepository.findById(id);
	}

    public ResponseEntity<Object> deleteFlat(Integer id) {
        if (!flatRepository.existsById(id)) {
            return ResponseHandler.responseBuilder("Flat with ID " + id + " does not exist.", HttpStatus.NOT_FOUND, null);
        }

        flatRepository.deleteById(id);
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, null);
    }

}
