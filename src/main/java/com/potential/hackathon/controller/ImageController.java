package com.potential.hackathon.controller;

import com.potential.hackathon.dto.ImageResponseDto;
import com.potential.hackathon.entity.Images;
import com.potential.hackathon.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping
    public String getImages() {
        return "upload";
    }

    @PostMapping("/{postId}")
    public ResponseEntity<List<ImageResponseDto>> uploadImages(
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @PathVariable Long postId) {

        List<Images> uploadedImages = imageService.uploadFiles(multipartFiles, "egomoya", postId);
        List<ImageResponseDto> response = imageService.saveImageInfo(uploadedImages, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
