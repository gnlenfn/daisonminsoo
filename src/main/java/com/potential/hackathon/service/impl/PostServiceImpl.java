package com.potential.hackathon.service.impl;

import com.potential.hackathon.dto.request.PostDto;
import com.potential.hackathon.dto.response.PostResponseDto;
import com.potential.hackathon.dto.response.Response;
import com.potential.hackathon.dto.response.UserResponseDto;
import com.potential.hackathon.entity.Posts;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.PostRepository;
import com.potential.hackathon.service.PostService;
import com.potential.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public PostResponseDto createPost(PostDto postDto) {
        Posts post = new Posts();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPassword(postDto.getPassword());
        post.setUsers(userService.findUserId(postDto.getUserId()));
        Posts save = postRepository.save(post);

        return PostResponseDto.findFromPosts(save);

    }

    @Override
    public PostResponseDto updatePost(PostDto postDto, Long postId) {
        Posts post = findPostId(postId);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Posts result = postRepository.save(post);

        return PostResponseDto.findFromPosts(result);

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

    public List<PostResponseDto> fetchPostPagesBy(Long lastId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Posts> entityPage = postRepository.findByPostIdLessThanOrderByPostidDesc(lastId, pageRequest);
        List<Posts> content = entityPage.getContent();

        return content.stream()
                .map(PostResponseDto::findFromPosts)
                .collect(Collectors.toList());
    }

    @Override
    public Response validPassword(String password, Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );

        if (post.getPassword().equals(password)) {
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
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );
    }
}
