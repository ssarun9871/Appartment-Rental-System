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

import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.response.ResponseHandler;

class LandlordServiceTest {

    @Mock
    private LandlordRepository landlordRepository;

    @Mock
    private FlatService flatService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private LandlordService landlordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLandlord() {
        Landlord landlord = new Landlord();
        landlord.setUsername("testUser");

        when(landlordRepository.save(landlord)).thenReturn(landlord);

        Landlord result = landlordService.addLandlord(landlord);

        assertEquals("PENDING", result.getStatus());
        assertEquals(false, result.getBlocked());
        verify(landlordRepository, times(1)).save(landlord);
    }

    @Test
    void testLogin_Success() {
        Landlord landlord = new Landlord();
        landlord.setUsername("testUser");
        landlord.setPassword("testPassword");
        landlord.setStatus("APPROVED");
        landlord.setBlocked(false);

        when(landlordRepository.findByUsername("testUser")).thenReturn(landlord);

        ResponseEntity<Object> response = landlordService.login("testUser", "testPassword");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Success", ((Map<String, Object>) response.getBody()).get("message"));
        assertEquals(landlord, ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testLogin_NoAccountFound() {
        when(landlordRepository.findByUsername("testUser")).thenReturn(null);

        ResponseEntity<Object> response = landlordService.login("testUser", "testPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("No account found", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_IncorrectPassword() {
        Landlord landlord = new Landlord();
        landlord.setUsername("testUser");
        landlord.setPassword("correctPassword");

        when(landlordRepository.findByUsername("testUser")).thenReturn(landlord);

        ResponseEntity<Object> response = landlordService.login("testUser", "wrongPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect password provided", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_AccountNotApproved() {
        Landlord landlord = new Landlord();
        landlord.setUsername("testUser");
        landlord.setPassword("testPassword");
        landlord.setStatus("PENDING");

        when(landlordRepository.findByUsername("testUser")).thenReturn(landlord);

        ResponseEntity<Object> response = landlordService.login("testUser", "testPassword");

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Your account has not been approved yet", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testLogin_AccountBlocked() {
        Landlord landlord = new Landlord();
        landlord.setUsername("testUser");
        landlord.setPassword("testPassword");
        landlord.setStatus("APPROVED");
        landlord.setBlocked(true);

        when(landlordRepository.findByUsername("testUser")).thenReturn(landlord);

        ResponseEntity<Object> response = landlordService.login("testUser", "testPassword");

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Your account is blocked", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testGetAllLandlords() {
        Landlord landlord1 = new Landlord();
        Landlord landlord2 = new Landlord();

        when(landlordRepository.findAll()).thenReturn(Arrays.asList(landlord1, landlord2));

        List<Landlord> landlords = landlordService.getAllLandlord();

        assertEquals(2, landlords.size());
        assertEquals(landlord1, landlords.get(0));
        assertEquals(landlord2, landlords.get(1));
    }

    @Test
    void testGetFlatByLandlord() {
        Integer landlordId = 1;
        ResponseEntity<Object> flatResponse = ResponseHandler.responseBuilder("Success", HttpStatus.OK, "Flat Data");

        when(flatService.getFlatByLandlord(landlordId)).thenReturn(flatResponse);

        ResponseEntity<Object> response = landlordService.getFlatByLandlord(landlordId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Flat Data", ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testGetBookingByLandlord() {
        Integer landlordId = 1;
        ResponseEntity<Object> bookingResponse = ResponseHandler.responseBuilder("Success", HttpStatus.OK, "Booking Data");

        when(bookingService.getBookingByFlat(landlordId)).thenReturn(bookingResponse);

        ResponseEntity<Object> response = landlordService.getBookingByLandlord(landlordId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Booking Data", ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testConfirmBooking() {
        Integer bookingId = 1;

        when(bookingService.confirmBookings(bookingId)).thenReturn("Booking confirmed");

        String response = landlordService.confirmBooking(bookingId);

        assertEquals("Booking confirmed", response);
    }

    @Test
    void testRejectBooking() {
        Integer bookingId = 1;

        when(bookingService.rejectBookings(bookingId)).thenReturn("Booking rejected");

        String response = landlordService.rejectBooking(bookingId);

        assertEquals("Booking rejected", response);
    }

    @Test
    void testBlockLandlord_Success() {
        Landlord landlord = new Landlord();
        landlord.setLandlord_id(1);

        when(landlordRepository.findById(1)).thenReturn(Optional.of(landlord));
        when(landlordRepository.save(landlord)).thenReturn(landlord);

        String response = landlordService.blockLandlord(1);

        assertEquals("Landlord blocked successfully", response);
        assertEquals(true, landlord.getBlocked());
        verify(landlordRepository, times(1)).save(landlord);
    }

    @Test
    void testBlockLandlord_NotFound() {
        when(landlordRepository.findById(1)).thenReturn(Optional.empty());

        String response = landlordService.blockLandlord(1);

        assertEquals("Landlord not found", response);
        verify(landlordRepository, times(0)).save(any(Landlord.class));
    }

    @Test
    void testDeleteLandlord_Success() {
        when(landlordRepository.existsById(1)).thenReturn(true);

        String response = landlordService.deleteLandlord(1);

        assertEquals("Landlord deleted successfully", response);
        verify(landlordRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteLandlord_NotFound() {
        when(landlordRepository.existsById(1)).thenReturn(false);

        String response = landlordService.deleteLandlord(1);

        assertEquals("Landlord not found", response);
        verify(landlordRepository, times(0)).deleteById(any(Integer.class));
    }
}
