package com.web.Mlog.controller;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public List<PostDto.PostListDto> postList() {
        return postService.getPostList();
    }

    /**
     * 포스트 단건 조회
     * */
    @GetMapping("/{postId}")
    public PostDto.PostDetailsDto postDetails(@PathVariable long postId) {
        return postService.getPostDetail(postId);
    }
//
//    /**
//     * 포스트 등록 처리
//     * */
//    @PostMapping("")
//    public PostDto.PostAddDto postAdd() {
//
//    }
//
//    /**
//     * 포스트 삭제 처리
//     * */
//    @DeleteMapping("")
//    public PostDto.PostDeleteDto postDelete() {
//
//    }
//
//    /**
//     * 포스트 수정 처리
//     * */
//    @PutMapping("")
//    public PostDto.PostModifyDto postModify() {
//
//    }

}
