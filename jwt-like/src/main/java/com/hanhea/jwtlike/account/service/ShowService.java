package com.hanhea.jwtlike.account.service;

import com.hanhea.jwtlike.account.dto.Response.CommonResponseDto;
import com.hanhea.jwtlike.account.dto.Response.PostresponseDto;
import com.hanhea.jwtlike.account.dto.Response.PostsresponseDto;
import com.hanhea.jwtlike.account.entity.Posts;
import com.hanhea.jwtlike.account.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public ResponseEntity showposts(){
        List<Posts> posts = postsRepository.findAll();
        List<PostsresponseDto> responselist = new LinkedList<>();
        for(Posts post: posts) {
            responselist.add(new PostsresponseDto(post.getCreateAt(), post.getAccount().getNickname()
                    , post.getId(), post.getLike(), post.getContent()));
        }
        System.out.println(responselist.size());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponseDto("success", responselist, 200));
    }

    @Transactional(readOnly = true)
    public ResponseEntity showpost(Long postid){
        try{
            Posts post = postsRepository.findById(postid).orElseThrow(
                    () -> new RuntimeException("찾는 post가 존재하지 않습니다.")
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success", new PostresponseDto(post), 200));
        }catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail", ex.getMessage(), 400));
        }
    }
}
