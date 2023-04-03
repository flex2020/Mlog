package com.web.Mlog.dto;

import com.web.Mlog.domain.Category;
import com.web.Mlog.domain.FileData;
import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        private List<String> fileList;
        private List<ReplyDto.ReplyListDto> replyList;
    }
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class PostAddDto {
        private int categoryId;
        private String title;
        private String content;
        private String previewContent;
        private List<FileData> fileList;
        private boolean visible;

        public Post toEntity(Category category) {
            Post post = new Post();
            post.setCategory(category);
            post.setTitle(this.title);
            post.setContent(this.content);
            post.setPreviewContent(this.previewContent);
            post.setPostedDate(LocalDateTime.now());
            post.setUpdatedDate(LocalDateTime.now());
            post.setFileList(this.fileList);
            post.setReplyList(new ArrayList<>());
            post.setVisible(this.visible);

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
