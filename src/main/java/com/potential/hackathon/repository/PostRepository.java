package com.potential.hackathon.repository;

import com.potential.hackathon.dto.request.PostDto;
import com.potential.hackathon.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    Slice<Posts> findSliceBy(Pageable pageable);

    @Query(value = "SELECT p FROM Posts p WHERE p.id < ?1 ORDER BY p.id DESC")
    Page<Posts> findByPostIdLessThanOrderByPostidDesc(Long lastId, PageRequest request);
}
