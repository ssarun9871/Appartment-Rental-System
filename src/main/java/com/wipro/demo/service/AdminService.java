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
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;

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
    private FlatRepository flatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public List<Tenant> viewSignupRequests() {
        
        return tenantRepository.findByStatus("PENDING");
    }

    public String approveSignupRequest(Integer tenantId) {
        Optional<Tenant> tenant = tenantRepository.findById(tenantId);
        if (tenant.isPresent()) {
            Tenant t = tenant.get();
            t.setStatus("APPROVED");
            tenantRepository.save(t);
            return "Tenant approved successfully.";
        } else {
            return "Tenant not found.";
        }
    }


    public void rejectSignupRequest(Integer tenantId) {
        tenantRepository.deleteById(tenantId);
    }

    public List<Flat> viewFlats() {
        return flatRepository.findAll();
    }

    public void addFlat(Flat flat) {
        flatRepository.save(flat);
    }

    public void deleteFlat(Integer flatId) {
        flatRepository.deleteById(flatId);
    }

    public List<Tenant> viewTenants() {
        return tenantRepository.findAll();
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
