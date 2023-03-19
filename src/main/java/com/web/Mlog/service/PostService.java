package com.web.Mlog.service;

import com.web.Mlog.domain.Post;
import com.web.Mlog.dto.PostDto;
import com.web.Mlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDto.PostListDto> getPostList() {
        List<Post> postList = postRepository.findAllByVisibleIsTrue();
        List<PostDto.PostListDto> response = new ArrayList<>();
        for (Post post : postList) {
            response.add(post.toPostListDto());
        }

        return response;
    }

    public PostDto.PostDetailsDto getPostDetail(long id) {
        Optional<Post> post = postRepository.findByPostIdAndVisibleIsTrue(id);
        if(post.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");

        return post.get().toDetailsDto();
    }
}
