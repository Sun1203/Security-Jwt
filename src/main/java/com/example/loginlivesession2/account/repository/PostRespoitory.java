package com.example.loginlivesession2.account.repository;

import com.example.loginlivesession2.account.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespoitory extends JpaRepository<Post, Long> {
}
