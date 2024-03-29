package com.potential.hackathon.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty
    private String content;
    @NotEmpty
    private String userId;
    private Long parentId;
}
