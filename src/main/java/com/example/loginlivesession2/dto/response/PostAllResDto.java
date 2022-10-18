package com.example.loginlivesession2.dto.response;

import com.example.loginlivesession2.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostAllResDto {

    private LocalDateTime createAt;
    private String nickname;
    private long postid;
    private int like;
    private String title;
    private String content;

    public static PostAllResDto toPostResDto(Post post) {
        return new PostAllResDto(
                post.getCreatedAt(),
                post.getNickname(),
                post.getPostId(),
                post.getPostlike(),
                post.getTitle(),
                post.getContent());
    }
}
