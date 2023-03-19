package com.web.Mlog.dto;

import com.web.Mlog.domain.FileData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


public class PostDto {
    @Getter
    @Setter
    @ToString
    public static class PostListDto {
        private long postId;
        private String title;
        private String category;
        private String content;
        private LocalDateTime postedDate;
        private String thumbnail;
        private int replyCount;
    }
    @Getter
    @Setter
    @ToString
    public static class PostDetailsDto {
        private long postId;
        private String title;
        private String category;
        private String content;
        private LocalDateTime postedDate;
        private List<String> fileList;
        private List<ReplyDto.ReplyListDto> replyList;
    }
    @Getter
    @Setter
    @ToString
    public static class PostAddDto {

    }
    @Getter
    @Setter
    @ToString
    public static class PostDeleteDto {

    }
    @Getter
    @Setter
    @ToString
    public static class PostModifyDto {

    }
}
