package com.wipro.demo.repository;

import com.wipro.demo.entity.Landlord;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LandlordRepository extends JpaRepository<Landlord, Integer> {
	List<Landlord> findByStatus(String status);
}