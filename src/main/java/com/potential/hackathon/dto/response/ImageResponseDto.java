package com.potential.hackathon.dto.response;

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
    private String uploadName;

    public static ImageResponseDto findFromImage(Images image) {
        return new ImageResponseDto(
                image.getUrl(),
                image.getPosts().getId(),
                image.getUploadName()
        );
    }
}
