package com.wipro.demo.service;

import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    
    public Tenant addTenant(Tenant tenant) {
    	tenant.setBlocked(false);
    	tenant.setStatus("PENDING");
    	tenant.setFlat(null);
        return tenantRepository.save(tenant);
    }

    public List<Tenant> viewTenants() {
        return tenantRepository.findAll();
    }

    public String blockTenant(Integer tenantId) {
        Optional<Tenant> tenant = tenantRepository.findById(tenantId);
        if (tenant.isPresent()) {
            Tenant t = tenant.get();
            t.setBlocked(true);
            tenantRepository.save(t);
            return "Tenant blocked successfully";
        } else {
            return "Tenant not found";
        }
    }

    public String deleteTenant(Integer tenantId) {
        if (tenantRepository.existsById(tenantId)) {
            tenantRepository.deleteById(tenantId);
            return "Tenant deleted successfully";
        } else {
            return "Tenant not found";
        }
    }
}

