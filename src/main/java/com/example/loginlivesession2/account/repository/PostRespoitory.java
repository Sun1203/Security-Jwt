package com.example.loginlivesession2.account.repository;

import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRespoitory extends JpaRepository<Post, Long> {
    Optional<Post> findByMember_Nickname(String nickname);
    List<Post> findByMember(Member member);


}
