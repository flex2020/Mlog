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
import java.util.stream.Collectors;

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


    /**
     * 포스트 목록
     * */
    @Transactional(readOnly = true)
    public List<PostDto.PostListDto> getPostList(int categoryId) {
        List<Post> postList;
        if (categoryId == 0) {
            postList = postRepository.findAllByVisibleTrue();
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."));
            postList = postRepository.findAllByCategoryAndVisibleTrue(category);
        }
        return postList.stream()
                .map(Post::toPostListDto)
                .collect(Collectors.toList());
    }

    /**
     * 포스트 상세보기
     * */
    @Transactional(readOnly = true)
    public PostDto.PostDetailsDto getPostDetail(int id) {
        Post post = postRepository.findByPostIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."));
        return post.toDetailsDto();
    }

    /**
     * 포스트 작성
     * */
    @Transactional
    public boolean addPost(PostDto.PostAddDto postAddDto) {
        Category category = categoryRepository.findById(postAddDto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."));
        try {
            List<FileData> fileList = fileDataRepository.findAllByUuidIn(postAddDto.getFileList());
            Post post = postRepository.save(postAddDto.toEntity(category, fileList));
            for (FileData fileData: fileList) {
                fileData.setPost(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 저장에 실패했습니다.");
        }
        return true;
    }

    /**
     * 포스트 삭제
     * */
    @Transactional
    public boolean deletePost(PostDto.PostDeleteDto postDeleteDto) {
        Post post = postRepository.findByPostIdAndVisibleIsTrue(postDeleteDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."));
        try {
            post.setVisible(false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 삭제를 실패했습니다.");
        }

        return true;
    }

    /**
     * 포스트 수정
     * */
    @Transactional
    public boolean modifyPost(PostDto.PostModifyDto postModifyDto) {
        Post post = postRepository.findByPostIdAndVisibleIsTrue(postModifyDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."));
        Category category = categoryRepository.findByCategoryName(postModifyDto.getCategory())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."));
        try {
            post.modifyPost(postModifyDto, category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "포스트 수정을 실패했습니다.");
        }
        return true;
    }

    /**
     * 댓글 목록
     * */
    public List<ReplyDto.ReplyListDto> getReplyList(int id) {
        return replyRepository.findAllByPostId(id).stream()
                .map(Reply::toReplyListDto)
                .collect(Collectors.toList());
    }


    /**
     * 댓글 작성
     * */
    @Transactional
    public boolean addReply(int postId, ReplyDto.ReplyAddDto replyAddDto) {
        Post post = postRepository.findByPostIdAndVisibleIsTrue(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."));
        replyRepository.save(replyAddDto.toEntity(post));
        return true;
    }

    /**
     * 댓글 삭제
     * */
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
    /**
     * 댓글 수정
     * */
    @Transactional
    public boolean modifyReply(int postId, ReplyDto.ReplyModifyDto replyModifyDto) {
        Reply reply = replyRepository.findByReplyIdAndVisibleIsTrue(replyModifyDto.getReplyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."));
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        if (!reply.getPassword().equals(replyModifyDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀립니다.");
        }
        try {
            reply.setContent(replyModifyDto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 수정에 실패했습니다.");
        }
        return true;
    }

}
