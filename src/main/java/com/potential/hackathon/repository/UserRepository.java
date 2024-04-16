package com.potential.hackathon.repository;

import com.potential.hackathon.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
    Optional<Users> findByNickname(String nickname);
}
