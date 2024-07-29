package com.wipro.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.TenantRepository;
import com.wipro.demo.response.ResponseHandler;

class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private FlatService flatService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private TenantService tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTenant() {
        Tenant tenant = new Tenant();
        tenant.setUsername("testUser");

        when(tenantRepository.save(tenant)).thenReturn(tenant);

        Tenant result = tenantService.addTenant(tenant);

        assertEquals("PENDING", result.getStatus());
        assertEquals(false, result.getBlocked());
        assertEquals(null, result.getFlat());
        verify(tenantRepository, times(1)).save(tenant);
    }

    @Test
    void testLogin_Success() {
        Tenant tenant = new Tenant();
        tenant.setUsername("testUser");
        tenant.setPassword("testPassword");
        tenant.setStatus("APPROVED");
        tenant.setBlocked(false);

        when(tenantRepository.findByUsername("testUser")).thenReturn(tenant);

        ResponseEntity<Object> response = tenantService.login("testUser", "testPassword");
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", ((Map<String, Object>) response.getBody()).get("message"));
        assertEquals(tenant, ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testLogin_NoAccountFound() {
        when(tenantRepository.findByUsername("testUser")).thenReturn(null);

        ResponseEntity<Object> response = tenantService.login("testUser", "testPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("No account found", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_IncorrectPassword() {
        Tenant tenant = new Tenant();
        tenant.setUsername("testUser");
        tenant.setPassword("correctPassword");

        when(tenantRepository.findByUsername("testUser")).thenReturn(tenant);

        ResponseEntity<Object> response = tenantService.login("testUser", "wrongPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect password provided", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_AccountNotApproved() {
        Tenant tenant = new Tenant();
        tenant.setUsername("testUser");
        tenant.setPassword("testPassword");
        tenant.setStatus("PENDING");

        when(tenantRepository.findByUsername("testUser")).thenReturn(tenant);

        ResponseEntity<Object> response = tenantService.login("testUser", "testPassword");

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Your account has not been approved yet", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_AccountBlocked() {
        Tenant tenant = new Tenant();
        tenant.setUsername("testUser");
        tenant.setPassword("testPassword");
        tenant.setStatus("APPROVED");
        tenant.setBlocked(true);

        when(tenantRepository.findByUsername("testUser")).thenReturn(tenant);

        ResponseEntity<Object> response = tenantService.login("testUser", "testPassword");

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Your account is blocked", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testGetAvailableFlats() {
        Flat flat1 = new Flat();
        Flat flat2 = new Flat();

        when(flatService.getAllAvailableFlats()).thenReturn(Arrays.asList(flat1, flat2));

        List<Flat> flats = tenantService.getAvailableFlats();

        assertEquals(2, flats.size());
        assertEquals(flat1, flats.get(0));
        assertEquals(flat2, flats.get(1));
    }

    @Test
    void testBookFlat() {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        ResponseEntity<Object> bookingResponse = ResponseHandler.responseBuilder("Success", HttpStatus.OK, "Booking Data");

        when(bookingService.addBooking(bookingRequestDTO)).thenReturn(bookingResponse);

        ResponseEntity<Object> response = tenantService.bookFlat(bookingRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Booking Data", ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testViewTenants() {
        Tenant tenant1 = new Tenant();
        Tenant tenant2 = new Tenant();

        when(tenantRepository.findAll()).thenReturn(Arrays.asList(tenant1, tenant2));

        List<Tenant> tenants = tenantService.viewTenants();

        assertEquals(2, tenants.size());
        assertEquals(tenant1, tenants.get(0));
        assertEquals(tenant2, tenants.get(1));
    }

    @Test
    void testBlockTenant_Success() {
        Tenant tenant = new Tenant();
        tenant.setTenant_id(1);

        when(tenantRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(tenantRepository.save(tenant)).thenReturn(tenant);

        String response = tenantService.blockTenant(1);

        assertEquals("Tenant blocked successfully", response);
        assertEquals(true, tenant.getBlocked());
        verify(tenantRepository, times(1)).save(tenant);
    }

    @Test
    void testBlockTenant_NotFound() {
        when(tenantRepository.findById(1)).thenReturn(Optional.empty());

        String response = tenantService.blockTenant(1);

        assertEquals("Tenant not found", response);
        verify(tenantRepository, times(0)).save(any(Tenant.class));
    }

    @Test
    void testDeleteTenant_Success() {
        when(tenantRepository.existsById(1)).thenReturn(true);

        String response = tenantService.deleteTenant(1);

        assertEquals("Tenant deleted successfully", response);
        verify(tenantRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTenant_NotFound() {
        when(tenantRepository.existsById(1)).thenReturn(false);

        String response = tenantService.deleteTenant(1);

        assertEquals("Tenant not found", response);
        verify(tenantRepository, times(0)).deleteById(any(Integer.class));
    }

    @Test
    void testGetBookingsByTenantId() {
        Integer tenantId = 1;
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        when(bookingService.getBookingsByTenantId(tenantId)).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> bookings = tenantService.getBookingsByTenantId(tenantId);

        assertEquals(2, bookings.size());
        assertEquals(booking1, bookings.get(0));
        assertEquals(booking2, bookings.get(1));
    }
}
