package com.example.loginlivesession2.domain;

import com.example.loginlivesession2.dto.request.CommentReqDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
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
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @Column(name = "commentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentid;

    @ManyToOne
    @JoinColumn(name = "memberid", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;

    private String comment;


    public Comment(UserDetailsImpl userDetails, Post post, CommentReqDto commentReqDto) {
        this.member = userDetails.getAccount();
        this.post = post;
        this.comment = commentReqDto.getComment();
    }

    public void update(CommentReqDto commentReqDto) {
        this.comment = commentReqDto.getComment();
    }

}
