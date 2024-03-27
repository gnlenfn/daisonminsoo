package com.potential.hackathon.controller;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.dto.PageResponse;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<PageResponse<Object>> getAllComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        PageRequest pageable = PageRequest.of(0, (page - 1) * size + size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<CommentResponseDto> comments = commentService.findAllComments(pageable, postId);
        PageResponse<Object> response = PageResponse.builder()
                .data(comments.getContent())
                .pageInfo(
                        PageResponse.PageInfo.builder()
                                .pageNumber(comments.getNumber())
                                .pageSize(comments.getSize())
                                .isLast(comments.isLast())
                                .build()
                ).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentDto commentDto) {
        CommentResponseDto result = commentService.saveComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @DeleteMapping
    public ResponseEntity<Response> deleteComment(@RequestBody CommentDto commentDto) {
        Response response = commentService.deleteComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
