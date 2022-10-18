package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.domain.Comment;
import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByPost(Post post);

    Optional<List<Comment>> findByMember(Member member);
}
