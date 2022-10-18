package com.example.loginlivesession2.account.entity;


import com.example.loginlivesession2.account.dto.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(CommentRequestDto commentRequestDto, Post post, Member member) {
        this.comment = commentRequestDto.getComment();
        this.post = post;
        this.member = member;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

}
