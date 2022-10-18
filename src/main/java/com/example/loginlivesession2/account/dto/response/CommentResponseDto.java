package com.example.loginlivesession2.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String comment;
    private String nickname;
    private LocalDateTime createAt;
}
