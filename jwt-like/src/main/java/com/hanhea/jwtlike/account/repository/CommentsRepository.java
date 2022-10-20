package com.hanhea.jwtlike.account.repository;

import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.entity.Comments;
import com.hanhea.jwtlike.account.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByIdAndPostsAndAccount_Nickname(Long id, Posts post, String nickname);
}
