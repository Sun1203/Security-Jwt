package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.request.PostReqDto;
import com.example.loginlivesession2.dto.response.PostAllResDto;
import com.example.loginlivesession2.dto.response.PostResDto;
import com.example.loginlivesession2.dto.response.delDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public GlobalResDto<PostResDto> generatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid PostReqDto postReqDto) {
        Long memberid = userDetails.getAccount().getMemberid();
        return postService.generatePost(memberid, postReqDto);
    }

    @PutMapping("/post/{postid}")
    public GlobalResDto<PostResDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody PostReqDto postReqDto,
                                      @PathVariable Long postid) {
        return postService.updatePost(userDetails, postReqDto, postid);
    }

    @GetMapping("/show")
    public GlobalResDto<List<PostAllResDto>> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("/show/{postid}")
    public GlobalResDto<PostResDto> getPost(@PathVariable Long postid) {
        return postService.getPost(postid);
    }

    @DeleteMapping("/post/{postid}")
    public GlobalResDto<delDto> delPost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.delPost(postid, userDetails);
    }


}
