package com.hanhea.jwtlike.account.dto.Response;

import com.hanhea.jwtlike.account.entity.Comments;
import com.hanhea.jwtlike.account.entity.Posts;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PostresponseDto {
    private LocalDateTime creatAt;
    private String title;
    private String nickname;
    private Long postid;
    private int like;
    private String content;

    private List<Comments> comments;

    public PostresponseDto(Posts post){
        this.creatAt = post.getCreateAt();
        this.nickname = post.getAccount().getNickname();
        this.postid = post.getId();
        this.like = post.getLike();
        this.content = post.getContent();
        this.comments = post.getComments();
        this.title = post.getTitle();
    }
}
