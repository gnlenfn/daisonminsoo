package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private String content;
    private Long postId;
    private String userId;

    public static CommentResponseDto findFromComment(Comments comment) {
        return new CommentResponseDto(
                comment.getContent(),
                comment.getPosts().getId(),
                comment.getUserId()
        );
    }

}
