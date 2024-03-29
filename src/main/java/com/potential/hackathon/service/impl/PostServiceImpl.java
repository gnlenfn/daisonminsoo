package com.potential.hackathon.service.impl;

import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
import com.potential.hackathon.dto.Response;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.PostRepository;
import com.potential.hackathon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponseDto createPost(PostDto postDto) {
        Posts post = new Posts();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPassword(postDto.getPassword());
        post.setUserId(postDto.getUserId());

        postRepository.save(post);

        return PostResponseDto.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .userId(postDto.getUserId())
                .build();

    }

    @Override
    public PostResponseDto updatePost(PostDto postDto, Long postId) {
        Posts post = findPostId(postId);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);

        return PostResponseDto.builder()
                .postId(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .userId(postDto.getUserId())
                .build();

    }

    @Override
    public Response deletePost(Long id) {
        postRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );
        postRepository.deleteById(id);

        return Response.builder()
                .result(Boolean.TRUE)
                .message("post deleted")
                .build();
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Posts post = findPostId(id);
        return PostResponseDto.findFromPosts(post);
    }

    @Override
    public Slice<PostResponseDto> findAllPosts(Pageable pageable) {
        Slice<Posts> posts = postRepository.findSliceBy(pageable);

        return posts.map(PostResponseDto::findFromPosts);
    }

    @Override
    public Response validPassword(PostDto postDto, Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );

        if (post.getPassword().equals(postDto.getPassword())) {
            return Response.builder()
                    .result(Boolean.TRUE)
                    .message("password match")
                    .build();
        }
        return Response.builder()
                .result(Boolean.FALSE)
                .message("password does not match")
                .build();
    }

    public Posts findPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND) // specify exception
        );
    }
}
