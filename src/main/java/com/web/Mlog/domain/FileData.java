package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FileData {
    @Id @Column
    @GeneratedValue
    private long fileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post")
    private Post post;
    @Column(length = 100)
    private String mediaType;
    @Column(length = 100)
    private String fileExtension;
    @Column(length = 500)
    private String filePath;
    private int width;
    private int height;
}
