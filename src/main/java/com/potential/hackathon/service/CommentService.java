package com.potential.hackathon.service;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.dto.Response;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    Slice<CommentResponseDto> findAllComments(Pageable pageable, Long postId);

    CommentResponseDto saveComment(CommentDto commentDto, Long postId);

    Response deleteComment(Long commentId);

    @Transactional
    CommentResponseDto editComment(CommentDto commentDto, Long commentId);
}
