package com.web.Mlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id @Column
    @GeneratedValue
    private long replyId;
    @Column(length = 50)
    private String writer;
    @Column(length = 1000)
    private String content;
    @Column(length = 500)
    private String password;
    @Column(length = 50)
    private String salt;
    private LocalDateTime date;
}
