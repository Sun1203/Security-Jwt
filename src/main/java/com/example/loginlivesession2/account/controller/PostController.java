package com.example.loginlivesession2.account.controller;

import com.example.loginlivesession2.account.dto.request.PostRequestDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.PostService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/post")
    public ResponseDto<?> createPost(@RequestBody @Valid PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMember().getMemberId();
        return postService.createPost(requestDto, memberId);

    }

    @PutMapping(value = "/post/{id}")
    public ResponseDto<?> updatePost(@RequestBody PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @PathVariable Long id
    ) {
        Long memberId = userDetails.getMember().getMemberId();
        return postService.updatePost(requestDto, memberId, id);
    }

    @DeleteMapping(value = "/post/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMember().getMemberId();
        return postService.deleteOne(id, memberId);
    }

    @GetMapping(value = "/show")
    public ResponseDto<?> getPost() {
        return postService.getPost();
    }

    @GetMapping(value = "/show/{id}")
    public ResponseDto<?> getOnePost(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

}
