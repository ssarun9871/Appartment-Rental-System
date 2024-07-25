package com.wipro.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.demo.entity.Admin;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.AdminRepository;
import com.wipro.demo.repository.BookingRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private FlatService flatService; 

    @Autowired
    private BookingRepository bookingRepository;

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public List<Object> viewSignupRequests() {
        List<Tenant> pendingTenants = tenantRepository.findByStatus("PENDING");
        List<Landlord> pendingLandlords = landlordRepository.findByStatus("PENDING");
        
        List<Object> pendingRequests = new ArrayList<>();
        pendingRequests.addAll(pendingTenants);
        pendingRequests.addAll(pendingLandlords);
        
        return pendingRequests;
    }


    public String approveSignupRequest(String role, Integer id) {
        if ("landlord".equalsIgnoreCase(role)) {
            return landlordRepository.findById(id)
                .map(landlord -> {
                    landlord.setStatus("APPROVED");
                    landlordRepository.save(landlord);
                    return "Landlord approved successfully.";
                })
                .orElse("Landlord not found.");
        } else if ("tenant".equalsIgnoreCase(role)) {
            return tenantRepository.findById(id)
                .map(tenant -> {
                    tenant.setStatus("APPROVED");
                    tenantRepository.save(tenant);
                    return "Tenant approved successfully.";
                })
                .orElse("Tenant not found.");
        } else {
            return "Invalid role.";
        }
    }



    public String rejectSignupRequest(String role, Integer id) {
        if ("landlord".equalsIgnoreCase(role)) {
            return landlordRepository.findById(id)
                .map(landlord -> {
                    landlord.setStatus("REJECTED");
                    landlordRepository.save(landlord);
                    return "Landlord rejected successfully.";
                })
                .orElse("Landlord not found.");
        } else if ("tenant".equalsIgnoreCase(role)) {
            return tenantRepository.findById(id)
                .map(tenant -> {
                    tenant.setStatus("REJECTED");
                    tenantRepository.save(tenant);
                    return "Tenant rejected successfully.";
                })
                .orElse("Tenant not found.");
        } else {
            return "Invalid role.";
        }
    }


    public List<Tenant> viewTenants() {
        return tenantRepository.findAll();
    }
    public List<Flat> viewAllFlats() {
        return flatService.getAllFlats();
    }

    public Flat addFlat(Flat flat, Integer landlord_id) {
        return flatService.addFlat(flat, landlord_id);
    }

    public void deleteFlat(Integer id) {
        flatService.deleteFlat(id);
    }
    
    public void blockTenant(Integer tenantId) {
        Optional<Tenant> tenant = tenantRepository.findById(tenantId);
        if (tenant.isPresent()) {
            Tenant t = tenant.get();
            t.setBlocked(true);
            tenantRepository.save(t);
        }
    }

    public void deleteTenant(Integer tenantId) {
        tenantRepository.deleteById(tenantId);
    }

    public List<Booking> viewBookings() {
        return bookingRepository.findAll();
    }

    public void cancelBooking(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public List<Landlord> viewLandlords() {
        return landlordRepository.findAll();
    }

    public void blockLandlord(Integer landlordId) {
        Optional<Landlord> landlord = landlordRepository.findById(landlordId);
        if (landlord.isPresent()) {
            Landlord l = landlord.get();
            l.setBlocked(true);
            landlordRepository.save(l);
        }
    }

    public void deleteLandlord(Integer landlordId) {
        landlordRepository.deleteById(landlordId);
    }
}
