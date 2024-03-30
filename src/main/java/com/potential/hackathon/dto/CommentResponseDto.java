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
    private UUID userId;
    private List<CommentChildResponseDto> children;
    private LocalDateTime createdAt;

    public static CommentResponseDto findFromComment(Comments comment) {
        return comment.getIsDeleted() ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다", comment.getUsers().getId(), null, comment.getCreatedAt()) :
                new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUsers().getId(),
                        comment.getChildren().stream().map(CommentChildResponseDto::findFromComment).collect(Collectors.toList()),
                        comment.getCreatedAt()
        );
    }

}
