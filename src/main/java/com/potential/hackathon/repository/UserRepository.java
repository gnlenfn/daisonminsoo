package com.potential.hackathon.repository;


import com.potential.hackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Define custom queries if needed
}