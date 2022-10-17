package com.example.loginlivesession2.dto.response;

import com.example.loginlivesession2.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResDto {

    private LocalDateTime createdAt;
    private Long commentid;
    private String nickname;
    private String comment;

    public static CommentResDto toCommentResDto(Comment comment) {
        return new CommentResDto(
                comment.getCreatedAt(),
                comment.getCommentid(),
                comment.getMember().getNickname(),
                comment.getComment());
    }
}
