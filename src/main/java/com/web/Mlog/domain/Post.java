package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @Column
    @GeneratedValue
    private long postId;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "category_name")
    private Category category;
    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "LONG TEXT")
    private String content;
    private LocalDateTime postedDate;
    private LocalDateTime updatedDate;
    @OneToMany
    @JoinColumn(name="file_list")
    private List<FileData> fileList;
    @OneToMany
    @JoinColumn(name="reply_list")
    private List<Reply> replyList;

}
