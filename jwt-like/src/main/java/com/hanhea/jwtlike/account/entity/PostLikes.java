package com.hanhea.jwtlike.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="postlikes")
public class PostLikes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false, name = "post_id")
    private Posts post;

    public PostLikes(Account account, Posts post){
        this.account = account;
        this.post = post;
    }
}
