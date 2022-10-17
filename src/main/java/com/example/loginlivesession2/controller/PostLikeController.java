package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postid}")
    public GlobalResDto<?> plusLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postid) {
        return postLikeService.plusLike(userDetails, postid);
    }
}
