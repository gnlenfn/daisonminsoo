package com.potential.hackathon.dto.response;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private UserResponseDto user;
    private Long parent;
    private List<CommentChildResponseDto> children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean idDeleted;

    public static CommentResponseDto findFromComment(Comments comment) {
        Long parent;
        if (comment.getParent() != null) {
            parent = comment.getParent().getId();
        } else {
            parent = null;
        }

        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                UserResponseDto.findFromUsers(comment.getUsers()),
                parent,
                comment.getChildren().stream().map(CommentChildResponseDto::findFromComment).collect(Collectors.toList()),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getIsDeleted()
        );
    }

}
