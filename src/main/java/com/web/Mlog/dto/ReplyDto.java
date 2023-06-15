package com.web.Mlog.dto;

import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
import lombok.*;

import java.time.LocalDateTime;

public class ReplyDto {
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ReplyListDto {
        private long replyId;
        private String writer;
        private String content;
        private LocalDateTime date;
        private long toReply;
        private boolean visible;
        @Builder
        public ReplyListDto(long replyId, String writer, String content, LocalDateTime date, long toReply, boolean visible) {
            this.replyId = replyId;
            this.writer = writer;
            this.content = content;
            this.date = date;
            this.toReply = toReply;
            this.visible = visible;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ReplyAddDto {
        private String writer;
        private String content;
        private String password;
        private boolean visible;

        public Reply toEntity(Post post) {
            Reply reply = new Reply();

            reply.setPost(post);
            reply.setWriter(this.writer);
            reply.setContent(this.content);
            reply.setPassword(this.password);
            reply.setSalt("");
            reply.setDate(LocalDateTime.now());
            reply.setVisible(this.visible);

            return reply;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ReplyDeleteDto {
        private long replyId;
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class ReplyModifyDto {
        private long replyId;
        private String content;
        private String password;
        private boolean visible;
    }
}
