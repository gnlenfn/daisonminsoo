package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentChildResponseDto {
    private Long id;
    private String content;
    private UUID uniqueUserId;

    public static CommentChildResponseDto findFromComment(Comments comment) {
        return comment.getIsDeleted() ?
                new CommentChildResponseDto(comment.getId(), "삭제된 댓글입니다", null) :
                new CommentChildResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUsers().getId()
                );
    }
}
