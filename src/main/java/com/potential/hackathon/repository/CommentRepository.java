package com.potential.hackathon.repository;

import com.potential.hackathon.entity.Comments;
import com.potential.hackathon.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    Page<Comments> findByPosts(Posts post, Pageable pageable);

    Page<Comments> findByPostsAndParentIsNull(Posts post, Pageable pageable);

    @Query(value = "SELECT COUNT(c) from Comments c where c.posts.id= :postId")
    Long countCommentsByPostId(Long postId);
}
