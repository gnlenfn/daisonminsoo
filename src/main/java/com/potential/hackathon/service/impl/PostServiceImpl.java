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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//        post.setImages(post.getImages());
        post.setPassword(postDto.getPassword());

        postRepository.save(post);

        return PostResponseDto.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

    }

    @Override
    public PostResponseDto updatePost(PostDto postDto, Long postId) {
        Posts post = findPostId(postId);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPassword(postDto.getPassword());

        postRepository.save(post);

        return PostResponseDto.builder()
                .postId(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

    }

    @Override
    public Response deletePost(PostDto postDto, Long id) {
        Posts post = postRepository.findById(id).orElseGet(Posts::new);
        if (post.getId() == null) {
            return Response.builder()
                    .result(Boolean.FALSE)
                    .message("There is no post")
                    .build();
        }
        else if (post.getPassword().equals(postDto.getPassword())) {
            postRepository.deleteById(id);
            return Response.builder()
                    .result(Boolean.TRUE)
                    .message("the post deleted")
                    .build();
        }
        return Response.builder()
                .result(Boolean.FALSE)
                .message("password does not match")
                .build();
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Posts post = findPostId(id);
        return PostResponseDto.findFromPosts(post);
    }

    @Override
    public Page<PostResponseDto> findAllPosts(Pageable pageable) {
        Page<Posts> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDto::findFromPosts);
    }

    public Posts findPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND) // specify exception
        );
    }
}
