package com.wipro.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.AdminRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;
import com.wipro.demo.response.ResponseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private LandlordRepository landlordRepository;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private FlatService flatService;

    @Mock
    private TenantService tenantService;

    @Mock
    private LandlordService landlordService;

    @Mock
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");

       when(adminRepository.findByUsername("admin")).thenReturn(admin);

        ResponseEntity<Object> response = adminService.login("admin", "password");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Success", responseBody.get("message"));
        assertNotNull(responseBody.get("data"));
    }

    @Test
    void testLogin_Failure_AccountNotFound() {
        when(adminRepository.findByUsername("admin")).thenReturn(null);

        ResponseEntity<Object> response = adminService.login("admin", "password");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("No account found", responseBody.get("message"));
    }

    @Test
    void testLogin_Failure_IncorrectPassword() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");

        when(adminRepository.findByUsername("admin")).thenReturn(admin);

        ResponseEntity<Object> response = adminService.login("admin", "wrongpassword");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Incorrect password!", responseBody.get("message"));
    }

    @Test
    void testViewSignupRequests() {
        Tenant tenant = new Tenant();
        tenant.setStatus("PENDING");

        Landlord landlord = new Landlord();
        landlord.setStatus("PENDING");

        when(tenantRepository.findByStatus("PENDING")).thenReturn(List.of(tenant));
        when(landlordRepository.findByStatus("PENDING")).thenReturn(List.of(landlord));

        List<Object> requests = adminService.viewSignupRequests();
        assertEquals(2, requests.size());
    }

    @Test
    void testApproveSignupRequest_Landlord() {
        Landlord landlord = new Landlord();
        landlord.setStatus("PENDING");

        when(landlordRepository.findById(1)).thenReturn(Optional.of(landlord));

        String response = adminService.approveSignupRequest("landlord", 1);
        assertEquals("Landlord approved successfully.", response);
        assertEquals("APPROVED", landlord.getStatus());
    }

    @Test
    void testRejectSignupRequest_Tenant() {
        Tenant tenant = new Tenant();
        tenant.setStatus("PENDING");

        when(tenantRepository.findById(1)).thenReturn(Optional.of(tenant));

        String response = adminService.rejectSignupRequest("tenant", 1);
        assertEquals("Tenant rejected successfully.", response);
        assertEquals("REJECTED", tenant.getStatus());
    }

    @Test
    void testViewAllFlats() {
        Flat flat = new Flat();
        when(flatService.getAllFlats()).thenReturn(List.of(flat));

        List<Flat> flats = adminService.viewAllFlats();
        assertNotNull(flats);
        assertEquals(1, flats.size());
    }

    @Test
    void testAddFlat() {
        Flat flat = new Flat();
        when(flatService.addFlat(flat, 1)).thenReturn(ResponseEntity.ok("Flat added"));

        ResponseEntity<Object> response = adminService.addFlat(flat, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Flat added", responseBody.get("message"));
    }

    @Test
    void testDeleteFlat() {
        when(flatService.deleteFlat(1)).thenReturn(ResponseEntity.ok("Flat deleted"));

        ResponseEntity<Object> response = adminService.deleteFlat(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Flat deleted", responseBody.get("message"));
    }

    @Test
    void testAddTenant() {
        Tenant tenant = new Tenant();
        when(tenantService.addTenant(tenant)).thenReturn(tenant);

        Tenant addedTenant = adminService.addTenant(tenant);
        assertNotNull(addedTenant);
    }

    @Test
    void testViewTenants() {
        Tenant tenant = new Tenant();
        when(tenantService.viewTenants()).thenReturn(List.of(tenant));

        List<Tenant> tenants = adminService.viewTenants();
        assertEquals(1, tenants.size());
    }

    @Test
    void testBlockTenant() {
        when(tenantService.blockTenant(1)).thenReturn("Tenant blocked");

        String response = adminService.blockTenant(1);
        assertEquals("Tenant blocked", response);
    }

    @Test
    void testDeleteTenant() {
        when(tenantService.deleteTenant(1)).thenReturn("Tenant deleted");

        String response = adminService.deleteTenant(1);
        assertEquals("Tenant deleted", response);
    }

    @Test
    void testViewLandlords() {
        Landlord landlord = new Landlord();
        when(landlordService.getAllLandlord()).thenReturn(List.of(landlord));

        List<Landlord> landlords = adminService.viewLandlords();
        assertEquals(1, landlords.size());
    }

    @Test
    void testBlockLandlord() {
        when(landlordService.blockLandlord(1)).thenReturn("Landlord blocked");

        String response = adminService.blockLandlord(1);
        assertEquals("Landlord blocked", response);
    }

    @Test
    void testDeleteLandlord() {
        when(landlordService.deleteLandlord(1)).thenReturn("Landlord deleted");

        String response = adminService.deleteLandlord(1);
        assertEquals("Landlord deleted", response);
    }

    @Test
    void testViewBookings() {
        Booking booking = new Booking();
        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        List<Booking> bookings = adminService.viewBookings();
        assertEquals(1, bookings.size());
    }

    @Test
    void testCancelBooking() {
        when(bookingService.cancelBookings(1)).thenReturn("Booking canceled");

        String response = adminService.cancelBooking(1);
        assertEquals("Booking canceled", response);
    }
}
