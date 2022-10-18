package com.example.loginlivesession2.account.dto.response;


import com.example.loginlivesession2.account.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostResponseDto {
    private LocalDateTime createAt;
    private String title;
    private String contents;
    private String nickname;
    private Long postId;
    private Long like;
    private List<CommentResponseDto> comment;
}
