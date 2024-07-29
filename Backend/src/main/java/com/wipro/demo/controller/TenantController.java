package com.wipro.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.service.BookingService;
import com.wipro.demo.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    TenantService tenantService;

    @Autowired 
    BookingService bookingService;

    @PostMapping("/signup")
    @Operation(summary = "Tenant signup", description = "Register a new tenant.")
    @ApiResponse(responseCode = "201", description = "Tenant successfully registered")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public Tenant signup(@RequestBody Tenant tenant) {
        return tenantService.addTenant(tenant);
    }

    @PostMapping("/login")
    @Operation(summary = "Tenant login", description = "Authenticate tenant user.")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Object> login(@RequestBody Tenant tenant) {
        return tenantService.login(tenant.getUsername(), tenant.getPassword());
    }

    @GetMapping("/available-flats")
    @Operation(summary = "Get available flats", description = "Retrieve a list of flats that are currently available.")
    @ApiResponse(responseCode = "200", description = "List of available flats")
    public List<Flat> getAvailableFlats() {
        return tenantService.getAvailableFlats();
    }

    @PostMapping("/book-flat")
    @Operation(summary = "Book a flat", description = "Request to book a flat.")
    @ApiResponse(responseCode = "200", description = "Booking request successful")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Object> bookFlat(
            @Parameter(description = "Booking request details", required = true) @RequestBody BookingRequestDTO bookingRequestDTO) {
        return tenantService.bookFlat(bookingRequestDTO);
    }

    @GetMapping("/bookings/{tenantId}")
    @Operation(summary = "Get tenant's bookings", description = "Retrieve all bookings made by a specific tenant.")
    @ApiResponse(responseCode = "200", description = "List of bookings")
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "404", description = "Tenant not found")
    public ResponseEntity<List<Booking>> getBookingsByTenantId(
            @Parameter(description = "Tenant ID", required = true) @PathVariable("tenantId") Integer tenantId) {
        List<Booking> bookings = tenantService.getBookingsByTenantId(tenantId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }
}
