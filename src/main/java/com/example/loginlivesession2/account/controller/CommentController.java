package com.example.loginlivesession2.account.controller;

import com.example.loginlivesession2.account.dto.request.CommentRequestDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.CommentService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/post/{id}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/comment")
    public ResponseDto<?> createComment(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        Long memberId = userDetails.getMember().getMemberId();
        return commentService.createComment(commentRequestDto, id, memberId);
    }

    @PutMapping(value = "/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long id,
                                        @PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return commentService.updateComment(id, commentId, memberId, commentRequestDto);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        @PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long memberId = userDetails.getMember().getMemberId();
        return commentService.deleteComment(id, commentId, memberId);
    }
}
