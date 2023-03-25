package com.web.Mlog.service;

import com.web.Mlog.domain.Post;
import com.web.Mlog.dto.PostDto;
import com.web.Mlog.repository.CategoryRepository;
import com.web.Mlog.repository.PostRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<PostDto.PostListDto> getPostList() {
        List<Post> postList = postRepository.findAllByVisibleIsTrue();
        Hibernate.initialize(postList);
        List<PostDto.PostListDto> response = new ArrayList<>();
        for (Post post : postList) {
            response.add(post.toPostListDto());
        }
        return response;
    }
    @Transactional(readOnly = true)
    public PostDto.PostDetailsDto getPostDetail(long id) {
        Optional<Post> post = postRepository.findByPostIdAndVisibleIsTrue(id);
        if(post.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");

        return post.get().toDetailsDto();
    }

    public boolean addPost(PostDto.PostAddDto postAddDto) {
        if (!categoryRepository.existsById(postAddDto.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다.");
        }
        return postRepository.save(postAddDto.toEntity()).getTitle().equals(postAddDto.getTitle());
    }

    @Transactional
    public boolean deletePost(PostDto.PostDeleteDto postDeleteDto) {
        if (!postRepository.existsById(postDeleteDto.getPostId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 포스트입니다.");
        }
        try {
            Post post = postRepository.findById(postDeleteDto.getPostId()).get();
            post.setVisible(false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 삭제를 실패했습니다.");
        }

        return true;
    }
}
