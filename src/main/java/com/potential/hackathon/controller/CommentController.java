package com.potential.hackathon.controller;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentResponseDto>> getAllComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<CommentResponseDto> comments = commentService.findAllComments(pageable, postId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);

    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentDto commentDto) {
        CommentResponseDto result = commentService.saveComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
