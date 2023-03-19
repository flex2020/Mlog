package com.web.Mlog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

public class ReplyDto {
    @Getter
    @Setter
    @ToString
    public static class ReplyListDto {
        private long replyId;
        private String writer;
        private String content;
        private LocalDateTime date;
    }
}
