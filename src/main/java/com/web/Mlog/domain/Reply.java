package com.web.Mlog.domain;

import com.web.Mlog.dto.PostDto;
import com.web.Mlog.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "REPLY_SEQ_GENERATOR"
        , sequenceName = "REPLY_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
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
    @ColumnDefault("-1")
    private long toReply;
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean visible;

    public ReplyDto.ReplyListDto toReplyListDto() {
        ReplyDto.ReplyListDto replyListDto = ReplyDto.ReplyListDto.builder()
                .replyId(replyId)
                .writer(writer)
                .content(content)
                .date(date)
                .toReply(toReply)
                .visible(visible)
                .build();
        return replyListDto;
    }
}
