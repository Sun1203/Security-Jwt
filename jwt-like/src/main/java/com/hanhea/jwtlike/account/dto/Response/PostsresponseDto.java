package com.hanhea.jwtlike.account.dto.Response;

import com.hanhea.jwtlike.account.entity.Comments;
import com.hanhea.jwtlike.account.entity.Posts;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PostsresponseDto {
    private LocalDateTime creatAt;
    private String nickname;
    private Long postid;
    private int like;
    private String content;
    private List<Comments> comments;

    public PostsresponseDto(LocalDateTime creatAt, String nickname, Long postid, int like, String content){
        this.creatAt = creatAt;
        this.nickname = nickname;
        this.postid = postid;
        this.like = like;
        this.content = content;
    }

    public PostsresponseDto(Posts post){
        this.creatAt = post.getCreateAt();
        this.nickname = post.getAccount().getNickname();
        this.postid = post.getId();
        this.like = post.getLike();
        this.content = post.getContent();
    }
}
