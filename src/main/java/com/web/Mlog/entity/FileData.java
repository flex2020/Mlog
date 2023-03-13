package com.web.Mlog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileData {
    @Id @Column
    @GeneratedValue
    private long fileId;
    @Column(length = 100)
    private String mediaType;
    @Column(length = 100)
    private String fileExtension;
    @Column(length = 500)
    private String filePath;
    private int width;
    private int height;
}
