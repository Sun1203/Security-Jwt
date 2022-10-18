package com.example.loginlivesession2.account.controller;


import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.PostLikeService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping(value = "/api/like/{id}")
    public ResponseDto<?> createPostLike(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return postLikeService.createPostLike(id, memberId);
    }

    @DeleteMapping(value = "/api/like/{id}")
    public ResponseDto<?> deletePostLike(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMember().getMemberId();
        return postLikeService.deletePostLike(id, memberId);
    }
}
