package com.potential.hackathon.controller;

import com.potential.hackathon.dto.response.ImageResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.entity.Images;
import com.potential.hackathon.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/{postId}")
    @Operation(summary = "이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업로드 성공",
                    content = {@Content(schema = @Schema(implementation = ImageResponseDto.class))})
    })
    public ResponseEntity<List<ImageResponseDto>> uploadImages(
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @PathVariable Long postId) {

        List<Images> uploadedImages = imageService.uploadFiles(multipartFiles, postId);
        List<ImageResponseDto> response = imageService.saveImageInfo(uploadedImages, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "이미지 삭제")
    public ResponseEntity<Response> deleteImage(@PathVariable Long imageId) {
        Response response = imageService.deleteImage(imageId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
