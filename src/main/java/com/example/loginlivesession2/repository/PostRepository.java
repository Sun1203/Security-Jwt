package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findByMember(Member member);
}
