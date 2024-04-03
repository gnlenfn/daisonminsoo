package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentChildResponseDto {
    private Long id;
    private String content;
    private UserResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    public static CommentChildResponseDto findFromComment(Comments comment) {
        return new CommentChildResponseDto(
                comment.getId(),
                comment.getContent(),
                UserResponseDto.findFromUsers(comment.getUsers()),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getIsDeleted()
        );
    }
}
