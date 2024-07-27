package com.wipro.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.demo.entity.Flat;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {
	List<Flat> findByAvailability(boolean availability);
    
}
