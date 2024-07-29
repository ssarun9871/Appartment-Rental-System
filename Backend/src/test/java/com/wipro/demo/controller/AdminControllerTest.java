package com.wipro.demo.controller;

import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    public AdminControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
        
        when(adminService.login("admin", "password")).thenReturn(ResponseEntity.ok().body("Login successful"));

        ResponseEntity<Object> response = adminController.login(admin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    void testViewSignupRequests() {
        List<Object> signupRequests = List.of(new Object());
        when(adminService.viewSignupRequests()).thenReturn(signupRequests);

        List<Object> response = adminController.viewSignupRequests();
        assertEquals(1, response.size());
    }

    @Test
    void testApproveSignupRequest() {
        when(adminService.approveSignupRequest("tenant", 1)).thenReturn("Request approved");

        String response = adminController.approveSignupRequest("tenant", 1);
        assertEquals("Request approved", response);
    }

    @Test
    void testRejectSignupRequest() {
        when(adminService.rejectSignupRequest("landlord", 1)).thenReturn("Request rejected");

        String response = adminController.rejectSignupRequest("landlord", 1);
        assertEquals("Request rejected", response);
    }

    @Test
    void testViewAllFlats() {
        Flat flat = new Flat();
        List<Flat> flats = List.of(flat);
        when(adminService.viewAllFlats()).thenReturn(flats);

        List<Flat> response = adminController.viewAllFlats();
        assertEquals(1, response.size());
    }

    @Test
    void testAddFlat() {
        Flat flat = new Flat();
        when(adminService.addFlat(flat, 1)).thenReturn(ResponseEntity.ok().body("Flat added"));

        ResponseEntity<Object> response = adminController.addFlat(flat, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Flat added", response.getBody());
    }

    @Test
    void testDeleteFlat() {
        when(adminService.deleteFlat(1)).thenReturn(ResponseEntity.ok().body("Flat deleted"));

        ResponseEntity<Object> response = adminController.deleteFlat(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Flat deleted", response.getBody());
    }

    @Test
    void testAddTenant() {
        Tenant tenant = new Tenant();
        when(adminService.addTenant(tenant)).thenReturn(tenant);

        Tenant response = adminController.addTenant(tenant);
        assertEquals(tenant, response);
    }

    @Test
    void testViewTenants() {
        Tenant tenant = new Tenant();
        List<Tenant> tenants = List.of(tenant);
        when(adminService.viewTenants()).thenReturn(tenants);

        List<Tenant> response = adminController.viewTenants();
        assertEquals(1, response.size());
    }

    @Test
    void testBlockTenant() {
        when(adminService.blockTenant(1)).thenReturn("Tenant blocked");

        String response = adminController.blockTenant(1);
        assertEquals("Tenant blocked", response);
    }

    @Test
    void testDeleteTenant() {
        when(adminService.deleteTenant(1)).thenReturn("Tenant deleted");

        String response = adminController.deleteTenant(1);
        assertEquals("Tenant deleted", response);
    }

    @Test
    void testViewLandlords() {
        Landlord landlord = new Landlord();
        List<Landlord> landlords = List.of(landlord);
        when(adminService.viewLandlords()).thenReturn(landlords);

        List<Landlord> response = adminController.viewLandlords();
        assertEquals(1, response.size());
    }

    @Test
    void testBlockLandlord() {
        when(adminService.blockLandlord(1)).thenReturn("Landlord blocked");

        String response = adminController.blockLandlord(1);
        assertEquals("Landlord blocked", response);
    }

    @Test
    void testDeleteLandlord() {
        when(adminService.deleteLandlord(1)).thenReturn("Landlord deleted");

        String response = adminController.deleteLandlord(1);
        assertEquals("Landlord deleted", response);
    }

    @Test
    void testViewBookings() {
        Booking booking = new Booking();
        List<Booking> bookings = List.of(booking);
        when(adminService.viewBookings()).thenReturn(bookings);

        List<Booking> response = adminController.viewBookings();
        assertEquals(1, response.size());
    }

    @Test
    void testCancelBooking() {
        when(adminService.cancelBooking(1)).thenReturn("Booking canceled");

        String response = adminController.cancelBooking(1);
        assertEquals("Booking canceled", response);
    }
}
