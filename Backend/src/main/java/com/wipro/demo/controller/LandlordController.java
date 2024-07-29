package com.wipro.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.service.FlatService;
import com.wipro.demo.service.LandlordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/landlord")
public class LandlordController {

    @Autowired
    LandlordService landlordService;

    @Autowired
    FlatService flatService;

    @PostMapping("/signup")
    @Operation(summary = "Landlord signup", description = "Register a new landlord.")
    @ApiResponse(responseCode = "201", description = "Landlord successfully registered")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public Landlord signup(@RequestBody Landlord landlord) {
        return landlordService.addLandlord(landlord);
    }

    @PostMapping("/login")
    @Operation(summary = "Landlord login", description = "Authenticate landlord user.")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Object> login(@RequestBody Landlord landlord) {
        return landlordService.login(landlord.getUsername(), landlord.getPassword());
    }

    @GetMapping("/flats")
    @Operation(summary = "Get landlord's flats", description = "Retrieve all flats associated with the landlord.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Landlord not found")
    public ResponseEntity<Object> getFlats(
            @Parameter(description = "Landlord ID", required = true) @RequestParam Integer landlordId) {
        return landlordService.getFlatByLandlord(landlordId);
    }

    @GetMapping("/bookings")
    @Operation(summary = "Get landlord's bookings", description = "Retrieve all bookings for the landlord.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Landlord not found")
    public ResponseEntity<Object> getBookings(
            @Parameter(description = "Landlord ID", required = true) @RequestParam Integer landlordId) {
        return landlordService.getBookingByLandlord(landlordId);
    }

    @PostMapping("/booking/confirm")
    @Operation(summary = "Confirm booking", description = "Confirm a booking request.")
    @ApiResponse(responseCode = "200", description = "Booking confirmed")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public String confirmBooking(
            @Parameter(description = "Booking ID", required = true) @RequestParam Integer bookingId) {
        return landlordService.confirmBooking(bookingId);
    }

    @PostMapping("/booking/reject")
    @Operation(summary = "Reject booking", description = "Reject a booking request.")
    @ApiResponse(responseCode = "200", description = "Booking rejected")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public String rejectBooking(
            @Parameter(description = "Booking ID", required = true) @RequestParam Integer bookingId) {
        return landlordService.rejectBooking(bookingId);
    }

    @PostMapping("/flat")
    @Operation(summary = "Add a flat", description = "Add a new flat to the landlord's portfolio.")
    @ApiResponse(responseCode = "201", description = "Flat added")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Object> addFlat(
            @Parameter(description = "Flat details", required = true) @RequestBody Flat flat,
            @Parameter(description = "Landlord ID", required = true) @RequestParam Integer landlord_id) {
        return flatService.addFlat(flat, landlord_id);
    }

    @DeleteMapping("/flats/{id}")
    @Operation(summary = "Delete a flat", description = "Remove a flat from the landlord's portfolio based on its ID.")
    @ApiResponse(responseCode = "200", description = "Flat deleted")
    @ApiResponse(responseCode = "404", description = "Flat not found")
    public ResponseEntity<Object> deleteFlat(
            @Parameter(description = "Flat ID", required = true) @PathVariable Integer id) {
        return flatService.deleteFlat(id);
    }
}
