package com.wipro.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Tenant;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByTenant(Optional<Tenant> tenant);
}
