package com.wipro.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}

