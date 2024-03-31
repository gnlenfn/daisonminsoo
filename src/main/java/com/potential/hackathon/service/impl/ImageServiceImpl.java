package com.potential.hackathon.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.potential.hackathon.dto.ImageResponseDto;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.entity.Images;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.ImageRepository;
import com.potential.hackathon.repository.PostRepository;
import com.potential.hackathon.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    @Value("${spring.s3.bucket}")
    private String bucketName;
    @Value("${spring.s3.postImagePath}")
    private String filePath;

    @Override
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }

    @Override
    public List<Images> uploadFiles(List<MultipartFile> multipartFiles,  Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );

        ArrayList<Images> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(Objects.requireNonNull(originalFileName));
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String keyName = filePath + "/" + uploadFileName;

                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );
                uploadFileUrl = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
            } catch (IOException e) {
                e.printStackTrace();
            }

            s3files.add(
                    Images.builder()
                            .uploadName(uploadFileName)
                            .url(uploadFileUrl)
                            .posts(post)
                            .build()
            );
        }
        return s3files;
    }

    @Override
    public List<ImageResponseDto> saveImageInfo(List<Images> images, Long postId) {
        for (Images file : images) {
            Images image = new Images();
            image.setUrl(file.getUrl());
            image.setUploadName(file.getUploadName());
            image.setPosts(postRepository.findById(postId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)));

            imageRepository.save(image);
        }

        return images.stream().map(ImageResponseDto::findFromImage).collect(Collectors.toList());
    }

    @Override
    public Response deleteImage(Long imageId) {
        Images image = imageRepository.findById(imageId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND));
        imageRepository.deleteById(imageId);
        String keyName = filePath + "/" + image.getUploadName();
        amazonS3Client.deleteObject("egomoya", keyName);
        return Response.builder()
                .result(Boolean.TRUE)
                .message("image deleted")
                .build();
    }
}

