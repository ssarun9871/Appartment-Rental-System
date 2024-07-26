package com.wipro.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.service.FlatService;
import com.wipro.demo.service.LandlordService;

@RestController
@RequestMapping("/landlord")
public class LandlordController {

	@Autowired
	LandlordService landlordService;

	@Autowired
	FlatService flatService;

	@PostMapping("/signup")
	public Landlord singup(@RequestBody Landlord landlord) {
		return landlordService.addLandlord(landlord);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Landlord landlord) {
		return landlordService.login(landlord.getUsername(), landlord.getPassword());
	}

	@PostMapping("/flat")
	public ResponseEntity<Object> addFlat(@RequestBody Flat flat, @RequestParam Integer landlord_id) {
		return flatService.addFlat(flat, landlord_id);

	}

	@DeleteMapping("/flats/{id}")
	public ResponseEntity<Object> deleteFlat(@PathVariable Integer id) {
		return flatService.deleteFlat(id);
	}
}
