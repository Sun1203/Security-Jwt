package com.example.loginlivesession2.account.controller;


import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.MyPageService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping(value = "/api/mypage/like")
    public ResponseDto<?> getLikePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return myPageService.getLikePage(memberId);
    }

    @GetMapping(value = "/api/mypage/post")
    public ResponseDto<?> getPostPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return myPageService.getPostPage(memberId);
    }

    @GetMapping(value = "/api/mypage/comment")
    public ResponseDto<?> getCommentPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return myPageService.getCommentPage(memberId);
    }
}