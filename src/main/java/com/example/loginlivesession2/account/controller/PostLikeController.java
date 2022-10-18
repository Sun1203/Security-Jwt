package com.example.loginlivesession2.account.controller;


import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.PostLikeService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping(value = "/{id}")
    public ResponseDto<?> createPostLike(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return postLikeService.createPostLike(id, memberId);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseDto<?> deletePostLike(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMember().getMemberId();
        return postLikeService.deletePostLike(id, memberId);
    }
}
