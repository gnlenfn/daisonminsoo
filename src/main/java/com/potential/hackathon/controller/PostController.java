package com.potential.hackathon.controller;

import com.potential.hackathon.dto.PageResponse;
import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/password/{postId}")
    @Operation(summary = "비밀번호 검증")
    public ResponseEntity<Response> validatePassword(@PathVariable @Schema(example = "1") Long postId,
                                                     @RequestHeader("password") String password) {
        Response response = postService.validPassword(password, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<PostResponseDto> patchPost(@PathVariable Long postId,
                                    @RequestBody @Valid PostDto postDto) {
        PostResponseDto result = postService.updatePost(postDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Response> deletePost(@PathVariable Long postId) {
        Response response = postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 단일 조회")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto post = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody @Valid PostDto body) {
        PostResponseDto post = postService.createPost(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    @Operation(summary = "게시글 리스트 조회")
    public ResponseEntity<PageResponse<Object>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(0, (page - 1) * size + size, Sort.by(Sort.Direction.DESC, "id"));
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
