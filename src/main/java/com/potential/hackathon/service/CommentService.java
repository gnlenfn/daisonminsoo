package com.potential.hackathon.service;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    Slice<CommentResponseDto> findAllComments(Pageable pageable, Long postId);

    CommentResponseDto saveComment(CommentDto commentDto);

    Response deleteComment(Long commentId);
}
