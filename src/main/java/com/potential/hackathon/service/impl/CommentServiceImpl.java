package com.potential.hackathon.service.impl;

import com.potential.hackathon.dto.request.CommentDto;
import com.potential.hackathon.dto.response.CommentResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.entity.Comments;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.CommentRepository;
import com.potential.hackathon.service.CommentService;
import com.potential.hackathon.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        Page<Comments> comments = commentRepository.findByPostsAndParentIsNull(post, pageable);
        return comments.map(CommentResponseDto::findFromComment);
    }

    @Override
    public CommentResponseDto createComment(CommentDto commentDto, Long postId) {
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
        Comments save = commentRepository.save(comment);

        return CommentResponseDto.findFromComment(save);
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
                .build();
    }

    @Override
    @Transactional
    public CommentResponseDto editComment(CommentDto commentDto, Long commentId) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);

        return CommentResponseDto.findFromComment(comment);
    }

    @Override
    public Long countCommentWithPostId(Long postId) {
        Posts post = postService.findPostId(postId);

        return commentRepository.countCommentsByPostId(post.getId());

    }
}
