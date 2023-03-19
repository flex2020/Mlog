package com.web.Mlog.domain;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reply {
    @Id @Column
    @GeneratedValue
    private long replyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private Post post;
    @Column(length = 50)
    private String writer;
    @Column(length = 1000)
    private String content;
    @Column(length = 500)
    private String password;
    @Column(length = 50)
    private String salt;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;

    public ReplyDto.ReplyListDto toReplyListDto() {
        ReplyDto.ReplyListDto replyListDto = new ReplyDto.ReplyListDto();
        replyListDto.setReplyId(this.replyId);
        replyListDto.setWriter(this.writer);
        replyListDto.setContent(this.content);
        replyListDto.setDate(this.date);

        return replyListDto;
    }
}
