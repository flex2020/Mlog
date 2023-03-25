package com.web.Mlog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FileData {
    @Id
    @Column(length = 50)
    private String uuid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post")
    private Post post;
    @Column(length = 100)
    private String mimeType;
    @Column(length = 500)
    private String fileName;
    private int width;
    private int height;
}
