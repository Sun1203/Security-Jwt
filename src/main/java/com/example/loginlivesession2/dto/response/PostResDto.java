package com.example.loginlivesession2.dto.response;

import com.example.loginlivesession2.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostResDto {

    private LocalDateTime createAt;
    private String nickname;
    private long postid;
    private int like;
    private String title;
    private String content;
    private List<CommentResDto> commentResDtoList;

    public static PostResDto toPostResDto(Post post, List<CommentResDto> commentResDtos) {
        return new PostResDto(
                post.getCreatedAt(),
                post.getNickname(),
                post.getPostId(),
                post.getPostlike(),
                post.getTitle(),
                post.getContent(),
                commentResDtos);
    }
}
