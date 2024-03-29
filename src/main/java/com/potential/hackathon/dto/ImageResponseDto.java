package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Images;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageResponseDto {

    private String imageUrl;
    private Long postId;

    public static ImageResponseDto findFromImage(Images image) {
        return new ImageResponseDto(
                image.getUrl(),
                image.getPosts().getId()
        );
    }
}
