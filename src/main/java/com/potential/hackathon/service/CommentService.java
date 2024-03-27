package com.potential.hackathon.service;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentResponseDto> findAllComments(Pageable pageable, Long postId);

    CommentResponseDto saveComment(CommentDto commentDto);
}
