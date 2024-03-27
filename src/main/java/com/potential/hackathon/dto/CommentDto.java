package com.potential.hackathon.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    @NotEmpty
    private String content;
    @NotEmpty
    private Long postId;
    private String userId;
}
