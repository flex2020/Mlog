package com.web.Mlog.service;

import com.web.Mlog.domain.Post;
import com.web.Mlog.dto.PostDto;
import com.web.Mlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
