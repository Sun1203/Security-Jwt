package com.example.loginlivesession2.domain;

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
@Table(name = "postlike")
public class PostLIke {

    @Id
    @Column(name = "postlikeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeid;

    @ManyToOne
    @JoinColumn(name = "memberid", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;

    public PostLIke(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
