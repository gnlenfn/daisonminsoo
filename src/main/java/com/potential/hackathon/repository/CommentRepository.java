package com.potential.hackathon.repository;

import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.entity.Comments;
import com.potential.hackathon.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    Page<Comments> findByPosts(Posts post, Pageable pageable);
}
