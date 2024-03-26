package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;

    public static PostResponseDto findFromPosts(Posts post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
