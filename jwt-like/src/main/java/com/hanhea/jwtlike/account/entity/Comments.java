package com.hanhea.jwtlike.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanhea.jwtlike.account.dto.Request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="comments")
public class Comments extends Timestamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Account account;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Posts posts;

    public Comments(CommentRequestDto commentRequestDto, Account account, Posts post){
        this.comment = commentRequestDto.getComment();
        this.account = account;
        this.posts = post;
    }
}
