package com.potential.hackathon.service.impl;

import com.potential.hackathon.dto.PostDto;
import com.potential.hackathon.dto.PostResponseDto;
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
    public Long createPost(PostDto postDto) {
        Posts post = new Posts();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
//        post.setImages(post.getImages());
        post.setPassword(postDto.getPassword());

        return postRepository.save(post).getId();
    }

    @Override
    public Long updatePost(PostDto postDto, Long postId) {
        Posts post = findPostId(postId);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return postRepository.save(post).getId();
    }

    @Override
    public Boolean deletePost(PostDto postDto, Long id) {
        Posts post = postRepository.findById(id).orElseGet(Posts::new);
        if (post.getId() == null) {
            return Boolean.FALSE;
        }
        else if (post.getPassword().equals(postDto.getPassword())) {
            postRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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

    private Posts findPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND) // specify exception
        );
    }
}
