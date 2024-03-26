package com.potential.hackathon.repository;

import com.potential.hackathon.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
