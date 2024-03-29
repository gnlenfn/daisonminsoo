package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Images;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDto {

    private Long id;

    @NotEmpty
    @Schema(description = "게시글 제목", example = "제목입니다")
    private String title;

    @NotEmpty
    @Schema(description = "게시글 내용", example = "this is contents of posting")
    private String content;

    private List<Images> images;

    @NotEmpty
    @Schema(description = "게시글 수정/삭제를 위한 비밀번호", example = "123456")
    private String password;

    @NotEmpty
    @Schema(description = "작성자 닉네임", example = "이거모야")
    private String userId;
}
