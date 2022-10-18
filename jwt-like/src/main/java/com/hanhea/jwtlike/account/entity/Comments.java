package com.hanhea.jwtlike.account.entity;

import com.hanhea.jwtlike.account.dto.Request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Account account;

    @ManyToOne
    private Posts post;

    public Comments(CommentRequestDto commentRequestDto, Account account, Posts post){
        this.comment = commentRequestDto.getComment();
        this.account = account;
        this.post = post;
    }
}
