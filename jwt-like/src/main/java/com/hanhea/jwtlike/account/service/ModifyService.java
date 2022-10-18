package com.hanhea.jwtlike.account.service;

import com.hanhea.jwtlike.account.dto.Request.CommentRequestDto;
import com.hanhea.jwtlike.account.dto.Request.PostRequestDto;
import com.hanhea.jwtlike.account.dto.Response.CommonResponseDto;
import com.hanhea.jwtlike.account.dto.Response.PostsresponseDto;
import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.entity.Comments;
import com.hanhea.jwtlike.account.entity.PostLikes;
import com.hanhea.jwtlike.account.entity.Posts;
import com.hanhea.jwtlike.account.repository.AccountRepository;
import com.hanhea.jwtlike.account.repository.CommnetsRepository;
import com.hanhea.jwtlike.account.repository.PostLikesRepository;
import com.hanhea.jwtlike.account.repository.PostsRepository;
import com.hanhea.jwtlike.jwt.jwt_utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ModifyService {
    private final PostsRepository postsRepository;
    private final PostLikesRepository postLikesRepository;
    private final AccountRepository accountRepository;
    private final CommnetsRepository commnetsRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public ResponseEntity addpost(String token, PostRequestDto requestDto) {
        try{
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을수 없습니다.")
                    );
            Posts post = new Posts(requestDto, account);
            postsRepository.save(post);
            System.out.println(post.getTitle());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            new PostsresponseDto(post),
                            200));
        }catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(),
                            400));
        }
    }

    @Transactional
    public ResponseEntity modipost(String token, PostRequestDto requestDto,
                                   Long postid) {
        try {
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을수 없습니다.")
                    );
            Posts post = postsRepository.findByIdAndAccount(postid, account).orElseThrow(
                    () -> new RuntimeException("해당 post를 수정할 권한이 없습니다.")
            );
            post.setTitle(requestDto.getTitle());
            post.setContent(requestDto.getContent());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            new PostsresponseDto(post),
                            200));

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(),
                            400));
        }
    }
    @Transactional
    public ResponseEntity delpost(String token, Long postid) {
        try {
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을수 없습니다.")
                    );
            Posts post = postsRepository.findByIdAndAccount(postid, account).orElseThrow(
                    () -> new RuntimeException("해당 post를 삭제할 권한이 없습니다.")
            );
            postsRepository.delete(post);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            new PostsresponseDto(post),
                            200));

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(),
                            400));
        }
    }

    //===============================================================
    @Transactional
    public ResponseEntity addcomment(String token, Long postid, CommentRequestDto requestDto) {
        try {
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname).orElseThrow(
                    () -> new RuntimeException("사용자가 없어요")
            );
            Posts post = postsRepository.findById(postid).orElseThrow(
                    () -> new RuntimeException("해당 포스터가 없어요"));
            Comments comments = new Comments(requestDto,account, post);
            commnetsRepository.save(comments);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            comments, 200));
        }catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(), 400));
        }
    }

    @Transactional
    public ResponseEntity modicomment(String token, Long postid, Long commentid, CommentRequestDto requestDto) {
        try {
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname).orElseThrow(
                    () -> new RuntimeException("사용자가 없어요")
            );
            Comments comments = commnetsRepository.findByIdAndPostAndAccount(commentid, postid, account).orElseThrow(
                    () -> new RuntimeException("권한에 해당하는 자료가 없어요")
            );
            comments.setComment(requestDto.getComment());
            commnetsRepository.save(comments);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            comments, 200));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(), 400));
        }
    }
    @Transactional
    public ResponseEntity delcomment(String token, Long postid, Long commentid) {
        try {
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname).orElseThrow(
                    () -> new RuntimeException("사용자가 없어요")
            );
            Comments comments = commnetsRepository.findByIdAndPostAndAccount(commentid, postid, account).orElseThrow(
                    () -> new RuntimeException("권한에 해당하는 자료가 없어요")
            );
            commnetsRepository.delete(comments);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            comments, 200));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(), 400));
        }
    }

    //=========================================================

    @Transactional
    public ResponseEntity liking(String token, Long postid){
        try{
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname).orElseThrow(
                    () -> new RuntimeException("사용자가 없어요")
            );
            Posts posts = postsRepository.findById(postid).orElseThrow(
                    () -> new RuntimeException("해당 포스트가 없어요")
            );
            if(postLikesRepository.existsByAccountAndPost(account, posts)){
                throw new RuntimeException("이미 좋아하셧어요");
            }
            PostLikes likes = new PostLikes(account, posts);
            postLikesRepository.save(likes);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            likes, 200));
        }   catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(), 400));
        }
    }

    @Transactional
    public ResponseEntity unlike(String token, Long postid){
        try{
            String nickname = jwtUtil.getNicknameFromToken(token);
            Account account = accountRepository.findByNickname(nickname).orElseThrow(
                    () -> new RuntimeException("사용자가 없어요")
            );
            Posts posts = postsRepository.findById(postid).orElseThrow(
                    () -> new RuntimeException("해당 포스트가 없어요")
            );
            if(!postLikesRepository.existsByAccountAndPost(account, posts)){
                throw new RuntimeException("이미 안좋아하시는 데요");
            }
            PostLikes likes = new PostLikes(account, posts);
            postLikesRepository.delete(likes);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success",
                            likes, 200));
        }   catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail",
                            ex.getMessage(), 400));
        }
    }


}
