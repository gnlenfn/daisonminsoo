package com.potential.hackathon.repository;

import com.potential.hackathon.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    Slice<Posts> findSliceBy(Pageable pageable);
}
