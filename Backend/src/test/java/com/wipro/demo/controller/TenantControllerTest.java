package com.wipro.demo.controller;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.service.BookingService;
import com.wipro.demo.service.TenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TenantControllerTest {

	@InjectMocks
	private TenantController tenantController;

	@Mock
	private TenantService tenantService;

	@Mock
	private BookingService bookingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSignup_Success() {
		Tenant tenant = new Tenant();
		tenant.setUsername("tenant");
		tenant.setPassword("password");

		when(tenantService.addTenant(tenant)).thenReturn(tenant);

		Tenant response = tenantController.signup(tenant);
		assertNotNull(response);
		assertEquals("tenant", response.getUsername());
	}

	@Test
	void testLogin_Success() {
		Tenant tenant = new Tenant();
		tenant.setUsername("tenant");
		tenant.setPassword("password");

		when(tenantService.login("tenant", "password")).thenReturn(ResponseEntity.ok("Login successful"));

		ResponseEntity<Object> response = tenantController.login(tenant);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login successful", response.getBody());
	}

	@Test
	void testLogin_Failure() {
		Tenant tenant = new Tenant();
		tenant.setUsername("tenant");
		tenant.setPassword("wrongpassword");

		when(tenantService.login("tenant", "wrongpassword"))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));

		ResponseEntity<Object> response = tenantController.login(tenant);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized", response.getBody());
	}

	@Test
	void testGetAvailableFlats_Success() {
		List<Flat> flats = Collections.singletonList(new Flat());
		when(tenantService.getAvailableFlats()).thenReturn(flats);

		List<Flat> response = tenantController.getAvailableFlats();
		assertNotNull(response);
		assertEquals(1, response.size());
	}

	@Test
	void testBookFlat_Success() {
		BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
		when(tenantService.bookFlat(bookingRequestDTO)).thenReturn(ResponseEntity.ok("Booking request successful"));

		ResponseEntity<Object> response = tenantController.bookFlat(bookingRequestDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Booking request successful", response.getBody());
	}

	@Test
	void testBookFlat_Failure() {
		BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
		when(tenantService.bookFlat(bookingRequestDTO))
				.thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request"));

		ResponseEntity<Object> response = tenantController.bookFlat(bookingRequestDTO);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Bad request", response.getBody());
	}

	@Test
	void testGetBookingsByTenantId_Success() {
		Integer tenantId = 1;
		List<Booking> bookings = Collections.singletonList(new Booking());
		when(tenantService.getBookingsByTenantId(tenantId)).thenReturn(bookings);

		ResponseEntity<List<Booking>> response = tenantController.getBookingsByTenantId(tenantId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().size());
	}

	@Test
	void testGetBookingsByTenantId_NoContent() {
		Integer tenantId = 1;
		when(tenantService.getBookingsByTenantId(tenantId)).thenReturn(Collections.emptyList());

		ResponseEntity<List<Booking>> response = tenantController.getBookingsByTenantId(tenantId);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(null, response.getBody());
	}

	@Test
	void testGetBookingsByTenantId_NotFound() {
		Integer tenantId = 1;

		when(tenantService.getBookingsByTenantId(tenantId)).thenReturn(Collections.emptyList());

		ResponseEntity<List<Booking>> response = tenantController.getBookingsByTenantId(tenantId);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		assertNull(response.getBody(), "Response body should be null for NO_CONTENT status");
	}

}
