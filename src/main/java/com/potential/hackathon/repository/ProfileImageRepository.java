package com.potential.hackathon.repository;

import com.potential.hackathon.entity.ProfileImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileImageRepository extends JpaRepository<ProfileImages, Long> {

    ProfileImages findByUsersId(Long id);

    @Query("SELECT p from ProfileImages p WHERE p.uploadName = :name")
    ProfileImages findByUploadName(@Param("name")String imageName);
}
