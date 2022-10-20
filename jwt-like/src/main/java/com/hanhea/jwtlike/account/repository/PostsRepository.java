package com.hanhea.jwtlike.account.repository;

import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findByIdAndAccount_Nickname(Long id, String nickname);
    List<Posts> findAll();
}
