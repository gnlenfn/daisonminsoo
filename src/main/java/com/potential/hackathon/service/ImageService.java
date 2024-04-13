package com.potential.hackathon.service;

import com.potential.hackathon.dto.response.ImageResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.entity.Images;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String getUuidFileName(String fileName);

    List<Images> uploadFiles(List<MultipartFile> multipartFiles, Long postId);

    List<ImageResponseDto> saveImageInfo(List<Images> images, Long postId);

    Response deleteImage(Long imageId);
}
