package com.web.Mlog.service;

import com.web.Mlog.domain.Category;
import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import com.web.Mlog.repository.CategoryRepository;
import com.web.Mlog.repository.PostRepository;
import com.web.Mlog.repository.ReplyRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, ReplyRepository replyRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.replyRepository = replyRepository;
    }



    @Transactional(readOnly = true)
    public List<PostDto.PostListDto> getPostList(int postId) {
        List<Post> postList = postRepository.findAllByPostIdAndVisibleTrue(postId);
        List<PostDto.PostListDto> response = new ArrayList<>();
        for (Post post : postList) {
            response.add(post.toPostListDto());
        }
        return response;
    }
    @Transactional(readOnly = true)
    public PostDto.PostDetailsDto getPostDetail(int id) {
        Optional<Post> post = postRepository.findByPostIdAndVisibleIsTrue(id);
        if(post.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");

        return post.get().toDetailsDto();
    }

    public boolean addPost(PostDto.PostAddDto postAddDto) {
        if (!categoryRepository.existsByCategoryName(postAddDto.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
        }
        Category category = categoryRepository.findByCategoryName(postAddDto.getCategoryName()).get();
        System.out.println(category);
        return postRepository.save(postAddDto.toEntity(category)).getTitle().equals(postAddDto.getTitle());
    }

    @Transactional
    public boolean deletePost(PostDto.PostDeleteDto postDeleteDto) {
        if (!postRepository.existsById(postDeleteDto.getPostId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        try {
            Post post = postRepository.findById(postDeleteDto.getPostId()).get();
            post.setVisible(false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 삭제를 실패했습니다.");
        }

        return true;
    }

    @Transactional
    public boolean modifyPost(PostDto.PostModifyDto postModifyDto) {
        if (!postRepository.existsById(postModifyDto.getPostId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (!categoryRepository.existsByCategoryName(postModifyDto.getCategory())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
        }
        try {
            Post post = postRepository.findById(postModifyDto.getPostId()).get();
            Category category = categoryRepository.findByCategoryName(postModifyDto.getCategory()).get();

            post.setCategory(category);
            post.setTitle(postModifyDto.getTitle());
            post.setContent(postModifyDto.getContent());
            post.setUpdatedDate(LocalDateTime.now());
            post.setThumbnail(postModifyDto.getThumbnail());
            post.setVisible(postModifyDto.isVisible());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 수정을 실패했습니다.");
        }
        return true;
    }

    public boolean addReply(int postId, ReplyDto.ReplyAddDto replyAddDto) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        try {
            Post post = postRepository.findByPostIdAndVisibleIsTrue(postId).get();
            replyRepository.save(replyAddDto.toEntity(post));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 작성을 실패했습니다.");
        }
        return true;
    }

    @Transactional
    public boolean deleteReply(int postId, ReplyDto.ReplyDeleteDto replyDeleteDto) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (!replyRepository.existsById(replyDeleteDto.getReplyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.");
        }
        Reply reply = replyRepository.findById(replyDeleteDto.getReplyId()).get();
        if (!reply.getPassword().equals(replyDeleteDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
        }
        try {
            reply.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 삭제를 실패했습니다.");
        }
        return true;
    }

    @Transactional
    public boolean modifyReply(int postId, ReplyDto.ReplyModifyDto replyModifyDto) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (!replyRepository.existsById(replyModifyDto.getReplyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.");
        }
        Reply reply = replyRepository.findById(replyModifyDto.getReplyId()).get();
        if (!reply.getPassword().equals(replyModifyDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다.");
        }
        try {
            reply.setContent(replyModifyDto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 수정을 실패했습니다.");
        }
        return true;
    }
}
