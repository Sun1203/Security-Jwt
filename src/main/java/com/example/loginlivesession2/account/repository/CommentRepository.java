package com.example.loginlivesession2.account.repository;

import com.example.loginlivesession2.account.dto.response.CommentResponseDto;
import com.example.loginlivesession2.account.entity.Comment;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByMember(Member member);
}
