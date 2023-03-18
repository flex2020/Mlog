package com.web.Mlog.dto;

import com.web.Mlog.domain.FileData;

import java.time.LocalDateTime;

public class PostDto {
    public static class PostListDto {
         public int postId;
         public String title;
         public String category;
         public String content;
         public LocalDateTime postedDate;
         public FileData thumbnail;
         public int replyCount;
    }

    public static class PostDetailsDto {

    }

    public static class PostAddDto {

    }

    public static class PostDeleteDto {

    }

    public static class PostModifyDto {

    }
}
