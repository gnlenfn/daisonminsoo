package com.potential.hackathon.dto.response;

import com.potential.hackathon.entity.ProfileImages;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ProfileImageResponseDto {
    private String imageUrl;
    private UUID userId;
    private String uploadName;

    public static ProfileImageResponseDto findFromProfileImage(ProfileImages image) {
        return new ProfileImageResponseDto(
                image.getUrl(),
                image.getUsers().getUserId(),
                image.getUploadName()
        );
    }
}
