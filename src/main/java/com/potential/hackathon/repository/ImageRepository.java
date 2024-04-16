package com.potential.hackathon.repository;

import com.potential.hackathon.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    @Query("SELECT p FROM Images p WHERE p.uploadName = :name")
    Images findByUploadName(@Param("name") String imageName);
    void deleteById(Long imageId);
}
