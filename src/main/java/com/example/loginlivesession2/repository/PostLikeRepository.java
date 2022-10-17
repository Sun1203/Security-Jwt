package com.example.loginlivesession2.repository;

import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import com.example.loginlivesession2.domain.PostLIke;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLIke, Long> {

    Optional<PostLIke> findAllByPostAndMember(Post post, Member member);
}
