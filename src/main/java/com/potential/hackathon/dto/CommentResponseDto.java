package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private UUID uniqueUserId;
    private List<CommentChildResponseDto> children;

    public static CommentResponseDto findFromComment(Comments comment) {
        return comment.getIsDeleted() ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다", null, null) :
                new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUsers().getId(),
                        comment.getChildren().stream().map(CommentChildResponseDto::findFromComment).collect(Collectors.toList())
        );
    }

}
