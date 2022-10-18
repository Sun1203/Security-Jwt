package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postid}")
    public GlobalResDto<?> plusLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postid) {
        return postLikeService.plusLike(userDetails, postid);
    }

    @DeleteMapping("/{postid}")
    public GlobalResDto<?> delLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postid){
        return postLikeService.delLike(userDetails,postid);
    }
}
