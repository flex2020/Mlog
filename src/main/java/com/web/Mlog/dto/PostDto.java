package com.web.Mlog.dto;

import com.web.Mlog.domain.Category;
import com.web.Mlog.domain.FileData;
import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PostDto {
    @Getter
    @Setter
    @ToString
    public static class PostListDto {
        private int postId;
        private String title;
        private String category;
        private String previewContent;
        private LocalDateTime postedDate;
        private String thumbnail;
        private int replyCount;
    }
    @Getter
    @Setter
    @ToString
    public static class PostDetailsDto {
        private int postId;
        private String title;
        private String category;
        private String content;
        private LocalDateTime postedDate;
        private List<ReplyDto.ReplyListDto> replyList;
    }
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class PostAddDto {
        private int categoryId;
        private String title;
        private String content;
        private String previewContent;
        private String thumbnail;
        private List<String> fileList;
        private boolean visible;

        public Post toEntity(Category category, List<FileData> fileList) {
            Post post = Post.builder()
                    .category(category)
                    .title(title)
                    .content(content)
                    .previewContent(previewContent)
                    .postedDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .fileList(fileList)
                    .thumbnail(thumbnail)
                    .replyList(new ArrayList<>())
                    .visible(visible)
                    .build();
            return post;
        }
    }
    @Getter
    @Setter
    @ToString
    public static class PostDeleteDto {
        private int postId;
    }
    @Getter
    @Setter
    @ToString
    public static class PostModifyDto {
        private int postId;
        private String category;
        private String title;
        private String content;
        private String previewContent;
        private LocalDateTime updatedDate;
        private String thumbnail;
        private boolean visible;
    }
}
