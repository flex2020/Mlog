package com.web.Mlog.dto;

import com.web.Mlog.domain.Post;
import com.web.Mlog.domain.Reply;
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
        private long toReply;
        private boolean visible;
    }

    @Getter
    @Setter
    @ToString
    public static class ReplyAddDto {
        private String writer;
        private String content;
        private String password;
        private long toReply;
        private boolean visible;

        public Reply toEntity(Post post) {
            Reply reply = new Reply();

            reply.setPost(post);
            reply.setWriter(this.writer);
            reply.setContent(this.content);
            reply.setPassword(this.password);
            reply.setSalt("");
            reply.setDate(LocalDateTime.now());
            reply.setToReply(this.toReply);
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
