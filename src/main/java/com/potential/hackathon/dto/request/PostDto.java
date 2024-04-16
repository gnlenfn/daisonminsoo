package com.potential.hackathon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostDto {

    @NotEmpty
    @Schema(description = "게시글 제목", example = "제목입니다")
    private String title;

    @NotEmpty
    @Schema(description = "게시글 내용", example = "this is contents of posting")
    private String content;

    private UUID userId;
}
