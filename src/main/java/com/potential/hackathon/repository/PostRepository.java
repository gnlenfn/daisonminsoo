package com.potential.hackathon.repository;

import com.potential.hackathon.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
