package com.hanhea.jwtlike.account.controller;

import com.hanhea.jwtlike.account.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @GetMapping(value = "/show/post")
    public ResponseEntity showposts(){
        return showService.showposts();
    }

    @GetMapping(value = "/show/{postid}")
    public ResponseEntity showpost(@PathVariable Long postid){
        return showService.showpost(postid);
    }
}
