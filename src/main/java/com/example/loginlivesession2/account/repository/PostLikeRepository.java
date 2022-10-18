package com.example.loginlivesession2.account.repository;

import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import com.example.loginlivesession2.account.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Long countByPost(Post post);
    Boolean existsByMemberAndPost(Member member, Post post);
    Integer deleteByMemberAndPost(Member member, Post post);

    List<PostLike> findByMember(Member member);
}
