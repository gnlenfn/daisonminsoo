package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private UserResponseDto user;
    private List<CommentChildResponseDto> children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto findFromComment(Comments comment) {
        return comment.getIsDeleted() ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다",
                        UserResponseDto.findFromUsers(comment.getUsers()), null,
                        comment.getCreatedAt(), comment.getUpdatedAt()
                ) :
                new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        UserResponseDto.findFromUsers(comment.getUsers()),
                        comment.getChildren().stream().map(CommentChildResponseDto::findFromComment).collect(Collectors.toList()),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
        );
    }

}
