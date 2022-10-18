package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/mypage/post")
    public GlobalResDto<?> getMypagePost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypagePost(userDetails);
    }

    @GetMapping("/mypage/comment")
    public GlobalResDto<?> getMypageComment(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypageComment(userDetails);
    }

    @GetMapping("/mypage/postlike")
    public GlobalResDto<?> getMypagePostLike(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypagePostLike(userDetails);
    }
}
