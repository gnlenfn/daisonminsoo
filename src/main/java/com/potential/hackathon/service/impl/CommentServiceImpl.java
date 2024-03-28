package com.potential.hackathon.service.impl;

import com.potential.hackathon.dto.CommentDto;
import com.potential.hackathon.dto.CommentResponseDto;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.entity.Comments;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.exceptions.NotFoundException;
import com.potential.hackathon.repository.CommentRepository;
import com.potential.hackathon.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostServiceImpl postService;
    private final CommentRepository commentRepository;

    @Override
    public Slice<CommentResponseDto> findAllComments(Pageable pageable, Long postId) {
        Posts post = postService.findPostId(postId);
        Page<Comments> comments = commentRepository.findByPosts(post, pageable);
        return comments.map(CommentResponseDto::findFromComment);
    }

    @Override
    public CommentResponseDto saveComment(CommentDto commentDto) {
        Comments comment = new Comments();
        Posts post = postService.findPostId(commentDto.getPostId());

        comment.setContent(commentDto.getContent());
        comment.setPosts(post);
        comment.setUserId(commentDto.getUserId());

        if (commentDto.getParentId() != null) {
            Comments parentComment = commentRepository.findById(commentDto.getParentId())
                    .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "There is no parent comment"));
            comment.setParent(parentComment);
        }
        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .content(commentDto.getContent())
                .postId(commentDto.getPostId())
                .userId(comment.getUserId())
                .build();
    }

    @Override
    public Response deleteComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );
        commentRepository.deleteById(commentId);

        return Response.builder()
                .result(Boolean.TRUE)
                .message("comment deleted")
                .build();
    }
}
