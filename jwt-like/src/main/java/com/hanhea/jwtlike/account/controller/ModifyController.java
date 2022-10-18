package com.hanhea.jwtlike.account.controller;

import com.hanhea.jwtlike.account.dto.Request.CommentRequestDto;
import com.hanhea.jwtlike.account.dto.Request.PostRequestDto;
import com.hanhea.jwtlike.account.service.ModifyService;
import com.hanhea.jwtlike.jwt.custom_Filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ModifyController {
    private final ModifyService modifyService;

    @PostMapping(value = "/addpost")
    public ResponseEntity addpost(@RequestHeader Map<String, String> data,
                                  @RequestBody PostRequestDto requestDto){
        return modifyService.addpost(data.get("access_token"), requestDto);
    }

    @PutMapping(value = "/post/{postid}")
    public ResponseEntity modipost(@RequestHeader Map<String, String> data,
                                   @RequestBody PostRequestDto requestDto,
                                   @PathVariable Long postid){
        return modifyService.modipost(data.get("access_token"), requestDto, postid);
    }

    @DeleteMapping(value = "/post/{postid}")
    public ResponseEntity delpost(@RequestHeader Map<String, String> data,
                                  @PathVariable Long postid){
        return modifyService.delpost(data.get("access_token"), postid);
    }



    @PostMapping(value = "/post/{postid}/comment")
    public ResponseEntity addcomment(@RequestHeader Map<String, String> data,
                                     @PathVariable Long postid,
                                     @RequestBody CommentRequestDto commentRequestDto){
        return modifyService.addcomment(data.get("access_token"), postid, commentRequestDto);
    }

    @PutMapping(value = "/post/{postid}/{commentid}")
    public ResponseEntity modicomment(@RequestHeader Map<String, String> data,
                                     @PathVariable Long postid, @PathVariable Long commentid,
                                     @RequestBody CommentRequestDto commentRequestDto){
        return modifyService.modicomment(data.get("access_token"), postid, commentid, commentRequestDto);
    }

    @DeleteMapping(value = "/post/{postid}/{commentid}")
    public ResponseEntity delcomment(@RequestHeader Map<String, String> data,
                                     @PathVariable Long postid, @PathVariable Long commentid){
        return modifyService.delcomment(data.get("access_token"), postid, commentid);
    }

    @PostMapping(value = "/like/{postid}")
    public ResponseEntity liking(@RequestHeader Map<String, String> data,
                                 @PathVariable Long postid){
        return modifyService.liking(data.get("access_token"), postid);
    }

    @DeleteMapping(value = "/like/{postid}")
    public ResponseEntity unlike(@RequestHeader Map<String, String> data,
                                 @PathVariable Long postid){
        return modifyService.unlike(data.get("access_token"), postid);
    }
}
