package com.potential.hackathon.service;

import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    Long createPost(PostDto postDto);

    Long updatePost(PostDto postDto, Long postId);

    Boolean deletePost(PostDto postDto, Long id);

    PostResponseDto getPost(Long id);

    Page<PostResponseDto> findAllPosts(Pageable pageable);
}
