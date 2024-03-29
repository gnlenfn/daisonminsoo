package com.potential.hackathon.service;

import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import com.potential.hackathon.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface PostService {
    PostResponseDto createPost(PostDto postDto);

    PostResponseDto updatePost(PostDto postDto, Long postId);

    Response deletePost(Long id);

    PostResponseDto getPost(Long id);

    Slice<PostResponseDto> findAllPosts(Pageable pageable);

    Response validPassword(String password, Long postId);
}
