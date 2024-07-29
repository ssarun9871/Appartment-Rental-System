package com.wipro.demo.controller;

import java.util.List;
import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @Operation(summary = "Admin login", description = "Authenticate admin user.")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Object> login(@RequestBody Admin admin) {
        return adminService.login(admin.getUsername(), admin.getPassword());
    }

    @GetMapping("/signup-requests")
    @Operation(summary = "View signup requests", description = "Retrieve all pending signup requests for tenants and landlords.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<Object> viewSignupRequests() {
        return adminService.viewSignupRequests();
    }

    @PostMapping("/approve-signup")
    @Operation(summary = "Approve signup request", description = "Approve a pending signup request based on role and ID.")
    @ApiResponse(responseCode = "200", description = "Request approved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public String approveSignupRequest(
            @Parameter(description = "Role of the user", required = true) @RequestParam String role,
            @Parameter(description = "ID of the user", required = true) @RequestParam Integer id) {
        return adminService.approveSignupRequest(role, id);
    }

    @PostMapping("/reject-signup")
    @Operation(summary = "Reject signup request", description = "Reject a pending signup request based on role and ID.")
    @ApiResponse(responseCode = "200", description = "Request rejected")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public String rejectSignupRequest(
            @Parameter(description = "Role of the user", required = true) @RequestParam String role,
            @Parameter(description = "ID of the user", required = true) @RequestParam Integer id) {
        return adminService.rejectSignupRequest(role, id);
    }

    @GetMapping("/flats")
    @Operation(summary = "View all flats", description = "Retrieve a list of all flats.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<Flat> viewAllFlats() {
        return adminService.viewAllFlats();
    }

    @PostMapping("/flats")
    @Operation(summary = "Add a flat", description = "Add a new flat to the system.")
    @ApiResponse(responseCode = "200", description = "Flat added")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Object> addFlat(
            @Parameter(description = "Flat details", required = true) @RequestBody Flat flat,
            @Parameter(description = "Landlord ID", required = true) @RequestParam Integer landlord_id) {
        return adminService.addFlat(flat, landlord_id);
    }

    @DeleteMapping("/flats/{id}")
    @Operation(summary = "Delete a flat", description = "Delete a flat based on its ID.")
    @ApiResponse(responseCode = "200", description = "Flat deleted")
    @ApiResponse(responseCode = "404", description = "Flat not found")
    public ResponseEntity<Object> deleteFlat(@PathVariable Integer id) {
        return adminService.deleteFlat(id);
    }

    @PostMapping("/tenants")
    @Operation(summary = "Add a tenant", description = "Add a new tenant to the system.")
    @ApiResponse(responseCode = "200", description = "Tenant added")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public Tenant addTenant(@RequestBody Tenant tenant) {
        return adminService.addTenant(tenant);
    }

    @GetMapping("/tenants")
    @Operation(summary = "View tenants", description = "Retrieve a list of all tenants.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<Tenant> viewTenants() {
        return adminService.viewTenants();
    }

    @PostMapping("/block-tenant")
    @Operation(summary = "Block a tenant", description = "Block a tenant based on their ID.")
    @ApiResponse(responseCode = "200", description = "Tenant blocked")
    @ApiResponse(responseCode = "404", description = "Tenant not found")
    public String blockTenant(@Parameter(description = "Tenant ID", required = true) @RequestParam Integer id) {
        return adminService.blockTenant(id);
    }

    @DeleteMapping("/delete-tenant")
    @Operation(summary = "Delete a tenant", description = "Delete a tenant based on their ID.")
    @ApiResponse(responseCode = "200", description = "Tenant deleted")
    @ApiResponse(responseCode = "404", description = "Tenant not found")
    public String deleteTenant(@Parameter(description = "Tenant ID", required = true) @RequestParam Integer id) {
        return adminService.deleteTenant(id);
    }

    @GetMapping("/landlords")
    @Operation(summary = "View landlords", description = "Retrieve a list of all landlords.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<Landlord> viewLandlords() {
        return adminService.viewLandlords();
    }

    @PostMapping("/block-landlord")
    @Operation(summary = "Block a landlord", description = "Block a landlord based on their ID.")
    @ApiResponse(responseCode = "200", description = "Landlord blocked")
    @ApiResponse(responseCode = "404", description = "Landlord not found")
    public String blockLandlord(@Parameter(description = "Landlord ID", required = true) @RequestParam Integer id) {
        return adminService.blockLandlord(id);
    }

    @DeleteMapping("/delete-landlord")
    @Operation(summary = "Delete a landlord", description = "Delete a landlord based on their ID.")
    @ApiResponse(responseCode = "200", description = "Landlord deleted")
    @ApiResponse(responseCode = "404", description = "Landlord not found")
    public String deleteLandlord(@Parameter(description = "Landlord ID", required = true) @RequestParam Integer id) {
        return adminService.deleteLandlord(id);
    }

    @GetMapping("/bookings")
    @Operation(summary = "View bookings", description = "Retrieve a list of all bookings.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<Booking> viewBookings() {
        return adminService.viewBookings();
    }

    @PostMapping("/cancel-booking")
    @Operation(summary = "Cancel a booking", description = "Cancel a booking based on its ID.")
    @ApiResponse(responseCode = "200", description = "Booking canceled")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public String cancelBooking(@Parameter(description = "Booking ID", required = true) @RequestParam Integer id) {
        return adminService.cancelBooking(id);
    }
}
