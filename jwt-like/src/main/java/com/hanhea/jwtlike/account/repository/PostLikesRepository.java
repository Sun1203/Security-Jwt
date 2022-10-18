package com.hanhea.jwtlike.account.repository;

import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.entity.PostLikes;
import com.hanhea.jwtlike.account.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Boolean existsByAccountAndPost(Account account, Posts post);
}
