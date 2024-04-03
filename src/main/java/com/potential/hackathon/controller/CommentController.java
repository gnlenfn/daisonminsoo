package com.potential.hackathon.controller;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.dto.PageResponse;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "댓글 조회")
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
                                .total(comments.getContent().size())
                                .build()
                ).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/{postId}")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody @Valid CommentDto commentDto,
                                                          @PathVariable Long postId) {
        CommentResponseDto result = commentService.createComment(commentDto, postId);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Response> deleteComment(@PathVariable Long commentId) {
        Response response = commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentResponseDto> patchComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId) {
        CommentResponseDto response = commentService.editComment(commentDto, commentId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/count/{postId}")
    @Operation(summary = "게시글 당 댓글 수 조회")
    public ResponseEntity<Long> test(@PathVariable Long postId) {
        Long result = commentService.countCommentWithPostId(postId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
