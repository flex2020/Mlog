package com.web.Mlog.service;

import com.web.Mlog.domain.Category;
import com.web.Mlog.domain.FileData;
import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import com.web.Mlog.repository.CategoryRepository;
import com.web.Mlog.repository.FileDataRepository;
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
    private FileDataRepository fileDataRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, ReplyRepository replyRepository, FileDataRepository fileDataRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.replyRepository = replyRepository;
        this.fileDataRepository = fileDataRepository;
    }



    @Transactional(readOnly = true)
    public List<PostDto.PostListDto> getPostList(int categoryId) {
        List<Post> postList;
        if (categoryId == 0) {
            postList = postRepository.findAllByVisibleTrue();
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
            postList = postRepository.findAllByCategoryAndVisibleTrue(optionalCategory.get());
        }
        List<PostDto.PostListDto> response = new ArrayList<>();
        for (Post post : postList) {
            response.add(post.toPostListDto());
        }
        return response;
    }
    @Transactional(readOnly = true)
    public PostDto.PostDetailsDto getPostDetail(int id) {
        Optional<Post> post = postRepository.findByPostIdAndVisibleIsTrue(id);
        if (post.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");

        return post.get().toDetailsDto();
    }

    public List<ReplyDto.ReplyListDto> getReplyList(int id) {
        List<ReplyDto.ReplyListDto> replyList = new ArrayList<>();
        List<Reply> all = replyRepository.findAllByPostId(id);
        for (Reply reply: all) {
            replyList.add(reply.toReplyListDto());
        }
        return replyList;
    }

    @Transactional
    public boolean addPost(PostDto.PostAddDto postAddDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(postAddDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
        }
        try {
            List<FileData> fileList = fileDataRepository.findAllByUuidIn(postAddDto.getFileList());
            Post post = postRepository.save(postAddDto.toEntity(optionalCategory.get(), fileList));
            for (FileData fileData: fileList) {
                System.out.println("file: " + fileData + ", post: " + post);
                fileData.setPost(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 저장에 실패했습니다.");
        }
        return true;
    }

    @Transactional
    public boolean deletePost(PostDto.PostDeleteDto postDeleteDto) {
        Optional<Post> optionalPost = postRepository.findByPostIdAndVisibleIsTrue(postDeleteDto.getPostId());
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        try {
            optionalPost.get().setVisible(false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 삭제를 실패했습니다.");
        }

        return true;
    }

    @Transactional
    public boolean modifyPost(PostDto.PostModifyDto postModifyDto) {
        Optional<Post> optionalPost = postRepository.findByPostIdAndVisibleIsTrue(postModifyDto.getPostId());
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(postModifyDto.getCategory());
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (optionalCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");
        }
        try {
            Post post = optionalPost.get();

            post.setCategory(optionalCategory.get());
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

    @Transactional
    public boolean addReply(int postId, ReplyDto.ReplyAddDto replyAddDto) {
        Post post = postRepository.findByPostIdAndVisibleIsTrue(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."));
        replyRepository.save(replyAddDto.toEntity(post));
        return true;
    }

    @Transactional
    public boolean deleteReply(int postId, ReplyDto.ReplyDeleteDto replyDeleteDto) {
        Reply reply = replyRepository.findByReplyIdAndVisibleIsTrue(replyDeleteDto.getReplyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."));

        if (reply.getPost().getPostId() != postId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (!reply.getPassword().equals(replyDeleteDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀립니다.");
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
        Optional<Reply> optionalReply = replyRepository.findByReplyIdAndVisibleIsTrue(replyModifyDto.getReplyId());
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (optionalReply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.");
        }
        Reply reply = optionalReply.get();
        if (!reply.getPassword().equals(replyModifyDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀립니다.");
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
