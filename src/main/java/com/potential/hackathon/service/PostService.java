package com.potential.hackathon.service;

import com.potential.hackathon.dto.request.PostDto;
import com.potential.hackathon.dto.response.PostResponseDto;
import com.potential.hackathon.dto.response.Response;
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
