package com.example.loginlivesession2.controller;

import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.request.CommentReqDto;
import com.example.loginlivesession2.dto.response.CommentResDto;
import com.example.loginlivesession2.dto.response.delDto;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import com.example.loginlivesession2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postid}/comment")
    public GlobalResDto<CommentResDto> generateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postid, @RequestBody CommentReqDto commentReqDto) {
        return commentService.generateComment(userDetails, postid, commentReqDto);
    }

    @PutMapping("/{postid}/{commentid}")
    public GlobalResDto<CommentResDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long postid, @PathVariable Long commentid, @RequestBody CommentReqDto commentReqDto) {
        return commentService.updateComment(userDetails, postid, commentid, commentReqDto);
    }

    @DeleteMapping("/{postid}/{commentid}")
    public GlobalResDto<delDto> delComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postid, @PathVariable Long commentid) {
        return commentService.delComment(userDetails, postid, commentid);
    }
}
