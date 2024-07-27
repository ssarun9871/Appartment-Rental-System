package com.wipro.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.service.BookingService;
import com.wipro.demo.service.TenantService;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	TenantService tenantService;
	@Autowired 
	BookingService bookingService;

	@PostMapping("/signup")
	public Tenant singup(@RequestBody Tenant tenant) {
		return tenantService.addTenant(tenant);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Tenant tenant) {
		return tenantService.login(tenant.getUsername(), tenant.getPassword());
	}
	
	@GetMapping("available-flats")
	public List<Flat> getAvailableFlats(){
		return tenantService.getAvailableFlats();
	}
	
    @PostMapping("/book-flat")
    public ResponseEntity<Object> bookFlat(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return tenantService.bookFlat(bookingRequestDTO);
    }
    
    @GetMapping("/bookings/{tenantId}")
    public ResponseEntity<List<Booking>> getBookingsByTenantId(@PathVariable("tenantId") Integer tenantId) {
        List<Booking> bookings = tenantService.getBookingsByTenantId(tenantId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }
}
