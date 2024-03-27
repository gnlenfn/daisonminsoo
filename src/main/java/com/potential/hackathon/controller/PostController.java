package com.potential.hackathon.controller;

import com.potential.hackathon.dto.PageResponse;
import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> patchPost(@PathVariable Long postId,
                                    @RequestBody @Validated PostDto postDto) {
        PostResponseDto result = postService.updatePost(postDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Response> deletePost(@PathVariable Long postId,
                                               @RequestBody @Validated PostDto postDto) {
        Response response = postService.deletePost(postDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto post = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> savePost(@RequestBody @Validated PostDto body) {
        PostResponseDto post = postService.createPost(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Object>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<PostResponseDto> posts = postService.findAllPosts(pageable);
        PageResponse<Object> result = PageResponse.builder()
                .data(posts.getContent())
                .pageInfo(
                        PageResponse.PageInfo.builder()
                                .pageNumber(posts.getNumber())
                                .isLast(posts.isLast())
                                .pageSize(posts.getSize()).build()
                ).build();
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
