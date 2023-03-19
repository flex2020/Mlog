package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id @Column
    @GeneratedValue
    private long postId;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "categoryName")
    private Category category;
    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime postedDate;
    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "post")
    private List<FileData> fileList;
    @OneToMany(mappedBy = "post")
    private List<Reply> replyList;

}
