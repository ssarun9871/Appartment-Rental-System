package com.wipro.demo.service;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.TenantRepository;
import com.wipro.demo.response.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private FlatService flatService;

	@Autowired
	private BookingService bookingService;

	public Tenant addTenant(Tenant tenant) {
		tenant.setBlocked(false);
		tenant.setStatus("PENDING");
		tenant.setFlat(null);
		return tenantRepository.save(tenant);
	}

	public ResponseEntity<Object> login(String username, String password) {
		Tenant tenant = tenantRepository.findByUsername(username);
		if (tenant == null) {
			return ResponseHandler.responseBuilder("No account found", HttpStatus.UNAUTHORIZED, null);
		}

		if (!tenant.getPassword().equals(password)) {
			return ResponseHandler.responseBuilder("Incorrect password provided", HttpStatus.UNAUTHORIZED, null);
		}
		if (!"APPROVED".equals(tenant.getStatus())) {
			return ResponseHandler.responseBuilder("Your account has not been approved yet", HttpStatus.FORBIDDEN,
					null);
		}
		if (tenant.getBlocked()) {
			return ResponseHandler.responseBuilder("Your account is blocked", HttpStatus.FORBIDDEN, null);
		}
		return ResponseHandler.responseBuilder("Success", HttpStatus.OK, tenant);
	}

	public List<Flat> getAvailableFlats() {
		return flatService.getAllAvailableFlats();
	}

	public ResponseEntity<Object> bookFlat(BookingRequestDTO bookingRequestDTO) {
		return bookingService.addBooking(bookingRequestDTO);
	}

	public List<Tenant> viewTenants() {
		return tenantRepository.findAll();
	}

	public String blockTenant(Integer tenantId) {
		Optional<Tenant> tenant = tenantRepository.findById(tenantId);
		if (tenant.isPresent()) {
			Tenant t = tenant.get();
			t.setBlocked(true);
			tenantRepository.save(t);
			return "Tenant blocked successfully";
		} else {
			return "Tenant not found";
		}
	}

	public String deleteTenant(Integer tenantId) {
		if (tenantRepository.existsById(tenantId)) {
			tenantRepository.deleteById(tenantId);
			return "Tenant deleted successfully";
		} else {
			return "Tenant not found";
		}
	}


	public List<Booking> getBookingsByTenantId(Integer tenant_id) {
		return bookingService.getBookingsByTenantId(tenant_id);

	}
}
