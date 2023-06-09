package com.web.Mlog.domain;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@NoArgsConstructor
@SequenceGenerator(
        name = "POST_SEQ_GENERATOR"
        , sequenceName = "POST_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class Post {
    @Id @Column
    @GeneratedValue
    private int postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name")
    private Category category;
    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime postedDate;
    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;
    @Column(length = 200)
    private String thumbnail;
    @Column(length = 200, nullable = false)
    private String previewContent;
    @OneToMany(mappedBy = "post")
    private List<FileData> fileList;

    @OneToMany(mappedBy = "post")
    private List<Reply> replyList;
    @Column(nullable = false)
    private boolean visible;


    @Builder
    public Post(int postId, Category category, String title, String content, LocalDateTime postedDate, LocalDateTime updatedDate, String thumbnail, String previewContent, List<FileData> fileList, List<Reply> replyList, boolean visible) {
        this.postId = postId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
        this.updatedDate = updatedDate;
        this.thumbnail = thumbnail;
        this.previewContent = previewContent;
        this.fileList = fileList;
        this.replyList = replyList;
        this.visible = visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void modifyPost(PostDto.PostModifyDto postModifyDto, Category category) {
        this.category = category;
        this.title = postModifyDto.getTitle();
        this.content = postModifyDto.getContent();
        this.updatedDate = LocalDateTime.now();
        this.thumbnail = postModifyDto.getThumbnail();
        this.visible = postModifyDto.isVisible();
    }

    /**
     * Entity -> DTO Convert
     * */
    public PostDto.PostListDto toPostListDto() {
        PostDto.PostListDto postListDto = PostDto.PostListDto.builder()
                .postId(postId)
                .title(title)
                .category(category.getCategoryName())
                .previewContent(previewContent)
                .postedDate(postedDate)
                .thumbnail(Optional.ofNullable(this.thumbnail).orElse("etc/no_image.png"))
                .replyCount(this.replyList.size())
                .build();
        return postListDto;
    }
    public PostDto.PostDetailsDto toDetailsDto() {
        PostDto.PostDetailsDto postDetailsDto = PostDto.PostDetailsDto.builder()
                .postId(this.postId)
                .title(this.title)
                .category(this.category.getCategoryName())
                .content(this.content)
                .postedDate(this.postedDate)
                .replyList(this.replyList.stream()
                        .map(Reply::toReplyListDto)
                        .collect(Collectors.toList()))
                .build();
        return postDetailsDto;
    }

}
