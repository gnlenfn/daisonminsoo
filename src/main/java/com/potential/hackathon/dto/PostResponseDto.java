package com.potential.hackathon.dto;

import com.potential.hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    // image, password 추가

    public static PostResponseDto findFromPosts(Posts post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
