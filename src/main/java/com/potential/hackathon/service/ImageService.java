package com.potential.hackathon.service;

import com.potential.hackathon.dto.response.ImageResponseDto;
import com.potential.hackathon.dto.response.ProfileImageResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.entity.Images;
import com.potential.hackathon.entity.ProfileImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    String getUuidFileName(String fileName);

    List<Images> uploadFiles(List<MultipartFile> multipartFiles, Long postId);

    List<ImageResponseDto> saveImageInfo(List<Images> images, Long postId);

    ProfileImages uploadProfileImage(MultipartFile image, UUID userId);

    ProfileImageResponseDto saveProfileImageInfo(ProfileImages image, UUID userId);

    ProfileImageResponseDto getUserProfile(UUID userId);

    Response deleteImage(String imageId, boolean isProfile);
}
