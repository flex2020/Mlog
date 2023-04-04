package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
    @Id
    @Column(length = 50)
    private String uuid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post")
    private Post post;
    @Column(length = 100, nullable = false)
    private String mimeType;
    @Column(length = 500, nullable = false)
    private String fileName;
    @Column(nullable = false)
    private long fileSize;
}
