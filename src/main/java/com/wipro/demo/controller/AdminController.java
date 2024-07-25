package com.wipro.demo.controller;

import java.util.List;
import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestBody Admin admin) {
        return adminService.login(admin.getUsername(), admin.getPassword());
    }


    @GetMapping("/signup-requests")
    public List<Object> viewSignupRequests() {
        return adminService.viewSignupRequests();
    }

    @PostMapping("/approve-signup")
    public String approveSignupRequest(@RequestParam String role, @RequestParam Integer id) {
        return adminService.approveSignupRequest(role, id);
    }


    @PostMapping("/reject-signup")
    public String rejectSignupRequest(@RequestParam String role, @RequestParam Integer id) {
        return adminService.rejectSignupRequest(role, id);
    }


    @GetMapping("/flats")
    public List<Flat> viewAllFlats() {
        return adminService.viewAllFlats();
    }

    @PostMapping("/flats")
    public Flat addFlat(@RequestBody Flat flat, @RequestParam Integer landlord_id) {
        return adminService.addFlat(flat, landlord_id);
    }

    @DeleteMapping("/flats/{id}")
    public void deleteFlat(@PathVariable Integer id) {
        adminService.deleteFlat(id);
    }

    @GetMapping("/tenants")
    public List<Tenant> viewTenants() {
        return adminService.viewTenants();
    }

    @PostMapping("/block-tenant")
    public void blockTenant(@RequestParam Integer tenantId) {
        adminService.blockTenant(tenantId);
    }

    @PostMapping("/delete-tenant")
    public void deleteTenant(@RequestParam Integer tenantId) {
        adminService.deleteTenant(tenantId);
    }

    @GetMapping("/bookings")
    public List<Booking> viewBookings() {
        return adminService.viewBookings();
    }

    @PostMapping("/cancel-booking")
    public void cancelBooking(@RequestParam Integer bookingId) {
        adminService.cancelBooking(bookingId);
    }

    @GetMapping("/landlords")
    public List<Landlord> viewLandlords() {
        return adminService.viewLandlords();
    }

    @PostMapping("/block-landlord")
    public void blockLandlord(@RequestParam Integer landlordId) {
        adminService.blockLandlord(landlordId);
    }

    @PostMapping("/delete-landlord")
    public void deleteLandlord(@RequestParam Integer landlordId) {
        adminService.deleteLandlord(landlordId);
    }
}
