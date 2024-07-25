package com.wipro.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wipro.demo.entity.Tenant;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    List<Tenant> findByStatus(String status);
}
