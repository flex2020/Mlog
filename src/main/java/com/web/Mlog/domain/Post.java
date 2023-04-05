package com.web.Mlog.domain;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * Entity -> DTO Convert
     * */
    public PostDto.PostListDto toPostListDto() {
        PostDto.PostListDto postListDto = new PostDto.PostListDto();
        postListDto.setPostId(this.postId);
        postListDto.setTitle(this.title);
        postListDto.setCategory(this.category.getCategoryName());
        postListDto.setPreviewContent(this.previewContent);
        postListDto.setPostedDate(this.postedDate);
        if (this.thumbnail != null) postListDto.setThumbnail(this.thumbnail);
        else postListDto.setThumbnail("/files/no_image.png");
        postListDto.setReplyCount(this.replyList.size());

        return postListDto;
    }
    public PostDto.PostDetailsDto toDetailsDto() {
        PostDto.PostDetailsDto postDetailsDto = new PostDto.PostDetailsDto();
        List<String> filePathList = new ArrayList<>();
        for(FileData fileData : this.fileList) filePathList.add(fileData.getFileName());

        List<ReplyDto.ReplyListDto> replyList = new ArrayList<>();
        for(Reply reply : this.replyList) replyList.add(reply.toReplyListDto());

        postDetailsDto.setPostId(this.postId);
        postDetailsDto.setTitle(this.title);
        postDetailsDto.setCategory(this.category.getCategoryName());
        postDetailsDto.setContent(this.content);
        postDetailsDto.setPostedDate(this.postedDate);
        postDetailsDto.setFileList(filePathList);
        postDetailsDto.setReplyList(replyList);

        return postDetailsDto;
    }
}
