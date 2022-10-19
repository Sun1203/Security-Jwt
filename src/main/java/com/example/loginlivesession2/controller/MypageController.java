package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.response.CommentResDto;
import com.example.loginlivesession2.dto.response.PostAllResDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/mypage/post")
    public GlobalResDto<List<PostAllResDto>> getMypagePost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypagePost(userDetails);
    }

    @GetMapping("/mypage/comment")
    public GlobalResDto<List<CommentResDto>> getMypageComment(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypageComment(userDetails);
    }

    @GetMapping("/mypage/postlike")
    public GlobalResDto<List<PostAllResDto>> getMypagePostLike(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypagePostLike(userDetails);
    }
}
