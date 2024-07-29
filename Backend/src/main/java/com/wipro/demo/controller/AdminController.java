package com.wipro.demo.controller;

import java.util.List;
import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Admin admin) {
		return adminService.login(admin.getUsername(), admin.getPassword());
	}

	@GetMapping("/signup-requests")
	public List<Object> viewSignupRequests() {
		return adminService.viewSignupRequests();
	}

	@PostMapping("/approve-signup")
	public String approveSignupRequest(@RequestParam String role, @RequestParam Integer id) {
		return adminService.approveSignupRequest(role, id);
	}

	@PostMapping("/reject-signup")
	public String rejectSignupRequest(@RequestParam String role, @RequestParam Integer id) {
		return adminService.rejectSignupRequest(role, id);
	}

	// ---------------------------------------FLAT------------------------------------------//
	@GetMapping("/flats")
	public List<Flat> viewAllFlats() {
		return adminService.viewAllFlats();
	}

	@PostMapping("/flats")
	public ResponseEntity<Object> addFlat(@RequestBody Flat flat, @RequestParam Integer landlord_id) {
		return adminService.addFlat(flat, landlord_id);
	}

	@DeleteMapping("/flats/{id}")
	public ResponseEntity<Object> deleteFlat(@PathVariable Integer id) {
		return adminService.deleteFlat(id);
	}

	// ---------------------------------------TENANTS------------------------------------------//
	@PostMapping("/tenants")
	public Tenant addTenant(@RequestBody Tenant tenant) {
		return adminService.addTenant(tenant);
	}

	@GetMapping("/tenants")
	public List<Tenant> viewTenants() {
		return adminService.viewTenants();
	}

	@PostMapping("/block-tenant")
	public String blockTenant(@RequestParam Integer id) {
		return adminService.blockTenant(id);
	}

	@DeleteMapping("/delete-tenant")
	public String deleteTenant(@RequestParam Integer id) {
		return adminService.deleteTenant(id);
	}

	// ---------------------------------------LANDLORD------------------------------------------//

	@GetMapping("/landlords")
	public List<Landlord> viewLandlords() {
		return adminService.viewLandlords();
	}

	@PostMapping("/block-landlord")
	public String blockLandlord(@RequestParam Integer id) {
		return adminService.blockLandlord(id);
	}

	@DeleteMapping("/delete-landlord")
	public String deleteLandlord(@RequestParam Integer id) {
		return adminService.deleteLandlord(id);
	}

	// ---------------------------------------BOOKINGS------------------------------------------//
	@GetMapping("/bookings")
	public List<Booking> viewBookings() {
		return adminService.viewBookings();
	}

	@PostMapping("/cancel-booking")
	public String cancelBooking(@RequestParam Integer id) {
		return adminService.cancelBooking(id);
	}
}
