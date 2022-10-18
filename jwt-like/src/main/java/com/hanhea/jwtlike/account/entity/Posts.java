package com.hanhea.jwtlike.account.entity;

import com.hanhea.jwtlike.account.dto.Request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="posts")
public class Posts extends Timestamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private List<Comments> comments;

    @Formula("(select count(1) from postlikes pl where pl.post_id = id)")
    private int like;

    public Posts(PostRequestDto postRequestDto, Account account){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.account = account;
    }
}
