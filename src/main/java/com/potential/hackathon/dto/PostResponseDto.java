package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Images;
import com.potential.hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String userId;
    private List<Images> images;

    public static PostResponseDto findFromPosts(Posts post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserId(),
                post.getImages()
        );
    }
}
