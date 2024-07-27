package com.wipro.demo.repository;

import com.wipro.demo.entity.Landlord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Integer> {
	List<Landlord> findByStatus(String status);

	Landlord findByUsername(String username);

}