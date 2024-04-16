package com.potential.hackathon.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.potential.hackathon.dto.response.ImageResponseDto;
import com.potential.hackathon.dto.response.ProfileImageResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.entity.Images;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.entity.ProfileImages;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.ImageRepository;
import com.potential.hackathon.repository.PostRepository;
import com.potential.hackathon.repository.ProfileImageRepository;
import com.potential.hackathon.repository.UserRepository;
import com.potential.hackathon.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;

    @Value("${spring.s3.bucket}")
    private String bucketName;
    @Value("${spring.s3.postImagePath}")
    private String postImagePath;
    @Value("${spring.s3.profileImagePath}")
    private String profilePath;

    @Override
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }

    @Override
    public List<Images> uploadFiles(List<MultipartFile> multipartFiles, Long postId) {
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
                String keyName = postImagePath + "/" + uploadFileName;

                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );
                uploadFileUrl = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new BusinessLogicException(ExceptionCode.IMAGE_SERVER_ERROR);
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
    public ProfileImages uploadProfileImage(MultipartFile image, UUID userId) {
        userRepository.findByUserId(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
        );

        String originalFilename = image.getOriginalFilename();
        String uuidFileName = getUuidFileName(Objects.requireNonNull(originalFilename));
        String uploadImgUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        objectMetadata.setContentType(image.getContentType());

        String keyName = profilePath + "/" + uuidFileName;
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, image.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_SERVER_ERROR);
        }
        uploadImgUrl = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;

        return ProfileImages.builder()
                .url(uploadImgUrl)
                .users(userRepository.findByUserId(userId).orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
                ))
                .uploadName(uuidFileName)
                .build();
    }

    @Override
    public ProfileImageResponseDto saveProfileImageInfo(ProfileImages image, UUID userId) {
        Users user = userRepository.findByUserId(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
        );

        ProfileImages result = ProfileImages.builder()
                .url(image.getUrl())
                .users(user)
                .uploadName(image.getUploadName())
                .build();

        profileImageRepository.save(result);
        return ProfileImageResponseDto.findFromProfileImage(result);
    }

    @Override
    public ProfileImageResponseDto getUserProfile(UUID userId) {
        Users user = userRepository.findByUserId(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
        );

        ProfileImages profile = profileImageRepository.findByUsersId(user.getId());

        return ProfileImageResponseDto.findFromProfileImage(profile);
    }

    @Override
    public Response deleteImage(String imageName, boolean isProfile) {
        try {
            if (isProfile) {
                ProfileImages profile = profileImageRepository.findByUploadName(imageName);
                profileImageRepository.deleteById(profile.getId());
                String keyName = profilePath + "/" + profile.getUploadName();
                amazonS3Client.deleteObject("egomoya", keyName);
            } else {
                Images image = imageRepository.findByUploadName(imageName);
                imageRepository.deleteById(image.getId());
                String keyName = postImagePath + "/" + image.getUploadName();
                amazonS3Client.deleteObject("egomoya", keyName);
            }
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }
        return Response.builder()
                .result(Boolean.TRUE)
                .message("image deleted")
                .build();
    }
}

