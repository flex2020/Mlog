package com.web.Mlog.controller;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import com.web.Mlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 포스트 목록
     * */
    @GetMapping("")
    public List<PostDto.PostListDto> postList(@RequestParam(name = "category", defaultValue = "0", required = false) int categoryId) {
        return postService.getPostList(categoryId);
    }

    /**
     * 포스트 단건 조회
     * */
    @GetMapping("/{postId}")
    public PostDto.PostDetailsDto postDetails(@PathVariable int postId) {
        return postService.getPostDetail(postId);
    }

    /**
     * 포스트 등록 처리
     * */
    @PostMapping("")
    public boolean postAdd(@RequestBody PostDto.PostAddDto postAddDto) {
        return postService.addPost(postAddDto);
    }

    /**
     * 포스트 삭제 처리
     * */
    @DeleteMapping("")
    public boolean postDelete(@RequestBody PostDto.PostDeleteDto postDeleteDto) {
        return postService.deletePost(postDeleteDto);
    }

    /**
     * 포스트 수정 처리
     * */
    @PutMapping("")
    public boolean postModify(@RequestBody PostDto.PostModifyDto postModifyDto) {
        return postService.modifyPost(postModifyDto);
    }

    /**
     * 포스트 댓글 작성
     * */
    @PostMapping("/{postId}/reply")
    public boolean replyAdd(@PathVariable int postId, @RequestBody ReplyDto.ReplyAddDto replyAddDto) {
        return postService.addReply(postId, replyAddDto);
    }

    /**
     * 포스트 댓글 삭제
     * */
    @DeleteMapping("/{postId}/reply")
    public boolean replyDelete(@PathVariable int postId, @RequestBody ReplyDto.ReplyDeleteDto replyDeleteDto) {
        return postService.deleteReply(postId, replyDeleteDto);
    }

    /**
     * 포스트 댓글 수정
     * */
    @PutMapping("/{postId}/reply")
    public boolean replyModify(@PathVariable int postId, @RequestBody ReplyDto.ReplyModifyDto replyModifyDto) {
        return postService.modifyReply(postId, replyModifyDto);
    }
}
