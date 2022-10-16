package com.example.loginlivesession2.account.controller;

import com.example.loginlivesession2.account.dto.request.PostRequestDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);

    }
}
