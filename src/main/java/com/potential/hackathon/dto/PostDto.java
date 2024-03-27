package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Images;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDto {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private List<Images> images;
    @NotEmpty
    private Integer password;
    @NotEmpty
    private String userId;
}
