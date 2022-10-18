package com.example.loginlivesession2.account.entity;


import com.example.loginlivesession2.account.dto.request.PostLikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Post post;

    public PostLike(Member member, Post post){
        this.member = member;
        this.post = post;
    }
}
