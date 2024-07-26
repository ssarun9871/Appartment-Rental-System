package com.wipro.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.AdminRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private LandlordRepository landlordRepository;

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private FlatService flatService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private LandlordService landlordService;

	@Autowired
	private BookingService bookingService;

	public Admin login(String username, String password) {
		Admin admin = adminRepository.findByUsername(username);
		if (admin != null && admin.getPassword().equals(password)) {
			return admin;
		}
		return null;
	}

	public List<Object> viewSignupRequests() {
		List<Tenant> pendingTenants = tenantRepository.findByStatus("PENDING");
		List<Landlord> pendingLandlords = landlordRepository.findByStatus("PENDING");

		List<Object> pendingRequests = new ArrayList<>();
		pendingRequests.addAll(pendingTenants);
		pendingRequests.addAll(pendingLandlords);

		return pendingRequests;
	}

	// ------------------------------SIGNUP REQUEST------------------------------//
	public String approveSignupRequest(String role, Integer id) {
		if ("landlord".equalsIgnoreCase(role)) {
			return landlordRepository.findById(id).map(landlord -> {
				landlord.setStatus("APPROVED");
				landlordRepository.save(landlord);
				return "Landlord approved successfully.";
			}).orElse("Landlord not found.");
		} else if ("tenant".equalsIgnoreCase(role)) {
			return tenantRepository.findById(id).map(tenant -> {
				tenant.setStatus("APPROVED");
				tenantRepository.save(tenant);
				return "Tenant approved successfully.";
			}).orElse("Tenant not found.");
		} else {
			return "Invalid role.";
		}
	}

	public String rejectSignupRequest(String role, Integer id) {
		if ("landlord".equalsIgnoreCase(role)) {
			return landlordRepository.findById(id).map(landlord -> {
				landlord.setStatus("REJECTED");
				landlordRepository.save(landlord);
				return "Landlord rejected successfully.";
			}).orElse("Landlord not found.");
		} else if ("tenant".equalsIgnoreCase(role)) {
			return tenantRepository.findById(id).map(tenant -> {
				tenant.setStatus("REJECTED");
				tenantRepository.save(tenant);
				return "Tenant rejected successfully.";
			}).orElse("Tenant not found.");
		} else {
			return "Invalid role.";
		}
	}

	// -------------------------------------FLAT---------------------------------//
	public List<Flat> viewAllFlats() {
		return flatService.getAllFlats();
	}

	public ResponseEntity<Object> addFlat(Flat flat, Integer landlord_id) {
		return flatService.addFlat(flat, landlord_id);
	}

	public ResponseEntity<Object> deleteFlat(Integer id) {
		return flatService.deleteFlat(id);
	}

	// -------------------------------------TENANT---------------------------------//
	public Tenant addTenant(Tenant tenant) {
		return tenantService.addTenant(tenant);
	}

	public List<Tenant> viewTenants() {
		return tenantService.viewTenants();
	}

	public String blockTenant(Integer tenantId) {
		return tenantService.blockTenant(tenantId);
	}

	public String deleteTenant(Integer tenantId) {
		return tenantService.deleteTenant(tenantId);
	}

	// -------------------------------------LANDLORD---------------------------------//
	public List<Landlord> viewLandlords() {
		return landlordService.getAllLandlord();
	}

	public String blockLandlord(Integer id) {
		return landlordService.blockLandlord(id);
	}

	public String deleteLandlord(Integer id) {
		return landlordService.deleteLandlord(id);
	}

	// -------------------------------------BOOKINGS---------------------------------//
	public List<Booking> viewBookings() {
		return bookingService.getAllBookings();
	}

	public String cancelBooking(Integer id) {
		return bookingService.cancelBookings(id);
	}
}
