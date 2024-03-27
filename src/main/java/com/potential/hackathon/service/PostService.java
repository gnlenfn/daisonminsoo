package com.potential.hackathon.service;

import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import com.potential.hackathon.dto.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostResponseDto createPost(PostDto postDto);

    PostResponseDto updatePost(PostDto postDto, Long postId);

    Response deletePost(PostDto postDto, Long id);

    PostResponseDto getPost(Long id);

    Page<PostResponseDto> findAllPosts(Pageable pageable);
}
