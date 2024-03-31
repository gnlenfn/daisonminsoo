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
import com.potential.hackathon.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostServiceImpl postService;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Slice<CommentResponseDto> findAllComments(Pageable pageable, Long postId) {
        Posts post = postService.findPostId(postId);
        Page<Comments> comments = commentRepository.findByPosts(post, pageable);
        return comments.map(CommentResponseDto::findFromComment);
    }

    @Override
    public CommentResponseDto saveComment(CommentDto commentDto, Long postId) {
        Comments comment = new Comments();
        Posts post = postService.findPostId(postId);

        comment.setContent(commentDto.getContent());
        comment.setPosts(post);
        comment.setUsers(userService.findUserId(UUID.fromString(commentDto.getUserId())));

        if (commentDto.getParentId() != null) {
            Comments parentComment = commentRepository.findById(commentDto.getParentId())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
            comment.setParent(parentComment);
        } else {
            comment.setParent(null);
        }
        commentRepository.save(comment);

        return CommentResponseDto.findFromComment(comment);
    }

    @Override
    @Transactional
    public Response deleteComment(Long commentId) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND)
        );
        comment.setIsDeleted(!comment.getIsDeleted());
        commentRepository.save(comment);
        return Response.builder()
                .result(Boolean.TRUE)
                .message("comment deleted")
                .build();
    }
}
