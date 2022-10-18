package com.example.loginlivesession2.domain;

import com.example.loginlivesession2.dto.request.PostReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @Column(name = "postid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String nickname;

    private long postlike;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberid", nullable = false)
    private Member member;

    public Post(Member member, PostReqDto postReqDto) {
        this.nickname = member.getNickname();
        this.title = postReqDto.getTitle();
        this.content = postReqDto.getContent();
        this.member = member;
    }

    public void update(PostReqDto postReqDto) {
        this.title = postReqDto.getTitle();
        this.content = postReqDto.getContent();
    }

    public void setPostlike(int postlike) {
        this.postlike += postlike;
    }
}
