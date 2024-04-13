package com.potential.hackathon.dto.response;

import com.potential.hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private UserResponseDto user;
    private List<ImageResponseDto> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseDto findFromPosts(Posts post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                UserResponseDto.findFromUsers(post.getUsers()),
                post.getImages().stream().map(ImageResponseDto::findFromImage).collect(Collectors.toList()),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
