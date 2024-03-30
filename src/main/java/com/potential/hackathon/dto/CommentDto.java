package com.potential.hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank
    @Schema(description = "댓글 내용", example = "coooooooooment" )
    private String content;
    @NotBlank
    @Schema(description = "작성자 ID", example = "user_id")
    private String userId;
    @Nullable
    @Schema(description = "부모 댓글 ID", example = "2")
    private Long parentId;
}
