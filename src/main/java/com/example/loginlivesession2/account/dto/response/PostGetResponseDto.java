package com.example.loginlivesession2.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostGetResponseDto {
    private LocalDateTime creatAt;
    private String nickname;
    private Long postId;
    private Long like;
    private String title;
    private String contents;
}
