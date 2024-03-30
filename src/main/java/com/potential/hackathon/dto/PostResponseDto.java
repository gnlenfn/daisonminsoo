package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private UUID userId;
    private List<ImageResponseDto> images;

    public static PostResponseDto findFromPosts(Posts post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUsers().getUniqueUserId(),
                post.getImages().stream().map(ImageResponseDto::findFromImage).collect(Collectors.toList())
        );
    }
}
