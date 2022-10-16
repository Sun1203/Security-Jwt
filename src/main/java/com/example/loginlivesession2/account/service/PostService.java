package com.example.loginlivesession2.account.service;

import com.example.loginlivesession2.account.dto.request.PostRequestDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.repository.PostRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRespoitory postRespoitory;

    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {


    }
}
