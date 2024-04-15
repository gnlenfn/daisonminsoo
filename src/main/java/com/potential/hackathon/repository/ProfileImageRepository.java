package com.potential.hackathon.repository;

import com.potential.hackathon.entity.ProfileImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImages, Long> {

    ProfileImages findByUsersId(Long id);
}
