package com.wipro.demo.controller;

import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.service.FlatService;
import com.wipro.demo.service.LandlordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LandlordControllerTest {

    @InjectMocks
    private LandlordController landlordController;

    @Mock
    private LandlordService landlordService;

    @Mock
    private FlatService flatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Success() {
        Landlord landlord = new Landlord();
        landlord.setUsername("landlord");
        landlord.setPassword("password");

        when(landlordService.addLandlord(landlord)).thenReturn(landlord);

        Landlord response = landlordController.signup(landlord);
        assertNotNull(response);
        assertEquals("landlord", response.getUsername());
    }

    @Test
    void testLogin_Success() {
        Landlord landlord = new Landlord();
        landlord.setUsername("landlord");
        landlord.setPassword("password");

        when(landlordService.login("landlord", "password")).thenReturn(
            ResponseEntity.ok("Login successful")
        );

        ResponseEntity<Object> response = landlordController.login(landlord);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    void testLogin_Failure() {
        Landlord landlord = new Landlord();
        landlord.setUsername("landlord");
        landlord.setPassword("wrongpassword");

        when(landlordService.login("landlord", "wrongpassword")).thenReturn(
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        );

        ResponseEntity<Object> response = landlordController.login(landlord);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testGetFlats_Success() {
        Integer landlordId = 1;
        when(landlordService.getFlatByLandlord(landlordId)).thenReturn(
            ResponseEntity.ok(Collections.singletonList(new Flat()))
        );

        ResponseEntity<Object> response = landlordController.getFlats(landlordId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetFlats_Failure() {
        Integer landlordId = 1;
        when(landlordService.getFlatByLandlord(landlordId)).thenReturn(
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Landlord not found")
        );

        ResponseEntity<Object> response = landlordController.getFlats(landlordId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Landlord not found", response.getBody());
    }

    @Test
    void testGetBookings_Success() {
        Integer landlordId = 1;
        when(landlordService.getBookingByLandlord(landlordId)).thenReturn(
            ResponseEntity.ok(Collections.singletonList(new Booking()))
        );

        ResponseEntity<Object> response = landlordController.getBookings(landlordId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookings_Failure() {
        Integer landlordId = 1;
        when(landlordService.getBookingByLandlord(landlordId)).thenReturn(
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Landlord not found")
        );

        ResponseEntity<Object> response = landlordController.getBookings(landlordId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Landlord not found", response.getBody());
    }

    @Test
    void testConfirmBooking_Success() {
        Integer bookingId = 1;
        when(landlordService.confirmBooking(bookingId)).thenReturn("Booking confirmed");

        String response = landlordController.confirmBooking(bookingId);
        assertEquals("Booking confirmed", response);
    }

    @Test
    void testConfirmBooking_Failure() {
        Integer bookingId = 1;
        when(landlordService.confirmBooking(bookingId)).thenReturn("Booking not found");

        String response = landlordController.confirmBooking(bookingId);
        assertEquals("Booking not found", response);
    }

    @Test
    void testRejectBooking_Success() {
        Integer bookingId = 1;
        when(landlordService.rejectBooking(bookingId)).thenReturn("Booking rejected");

        String response = landlordController.rejectBooking(bookingId);
        assertEquals("Booking rejected", response);
    }

    @Test
    void testRejectBooking_Failure() {
        Integer bookingId = 1;
        when(landlordService.rejectBooking(bookingId)).thenReturn("Booking not found");

        String response = landlordController.rejectBooking(bookingId);
        assertEquals("Booking not found", response);
    }

    @Test
    void testAddFlat_Success() {
        Flat flat = new Flat();
        Integer landlordId = 1;
        when(flatService.addFlat(flat, landlordId)).thenReturn(
            ResponseEntity.ok("Flat added")
        );

        ResponseEntity<Object> response = landlordController.addFlat(flat, landlordId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Flat added", response.getBody());
    }

    @Test
    void testAddFlat_Failure() {
        Flat flat = new Flat();
        Integer landlordId = 1;
        when(flatService.addFlat(flat, landlordId)).thenReturn(
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request")
        );

        ResponseEntity<Object> response = landlordController.addFlat(flat, landlordId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", response.getBody());
    }

    @Test
    void testDeleteFlat_Success() {
        Integer flatId = 1;
        when(flatService.deleteFlat(flatId)).thenReturn(
            ResponseEntity.ok("Flat deleted")
        );

        ResponseEntity<Object> response = landlordController.deleteFlat(flatId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Flat deleted", response.getBody());
    }

    @Test
    void testDeleteFlat_Failure() {
        Integer flatId = 1;
        when(flatService.deleteFlat(flatId)).thenReturn(
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flat not found")
        );

        ResponseEntity<Object> response = landlordController.deleteFlat(flatId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Flat not found", response.getBody());
    }
}
