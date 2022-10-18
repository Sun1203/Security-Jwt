package com.example.loginlivesession2.account.service;


import com.example.loginlivesession2.account.dto.response.CommentResponseDto;
import com.example.loginlivesession2.account.dto.response.PostGetResponseDto;
import com.example.loginlivesession2.account.dto.response.PostResponseDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.entity.Comment;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import com.example.loginlivesession2.account.entity.PostLike;
import com.example.loginlivesession2.account.repository.CommentRepository;
import com.example.loginlivesession2.account.repository.MemberRepository;
import com.example.loginlivesession2.account.repository.PostLikeRepository;
import com.example.loginlivesession2.account.repository.PostRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRespoitory postRespoitory;
    private final CommentRepository commentRepository;


    @Transactional
    public ResponseDto<?> getLikePage(Long memberId) {

        Member member = isPresentMember(memberId);

        List<PostLike> postLike = postLikeRepository.findByMember(member);
        List<PostGetResponseDto> responseList = new ArrayList<>();

        for (PostLike posts : postLike) {
            PostGetResponseDto responseDto= new PostGetResponseDto(
                    posts.getPost().getCreatedAt(),
                    posts.getPost().getMember().getNickname(),
                    posts.getPost().getPostId(),
                    postLikeRepository.countByPost(posts.getPost()),
                    posts.getPost().getTitle(),
                    posts.getPost().getContents()
            );
            responseList.add(responseDto);
        }
        return ResponseDto.success(responseList);
    }

    @Transactional
    public ResponseDto<?> getPostPage(Long memberId) {

        Member member = isPresentMember(memberId);

        List<Post>  postList = postRespoitory.findByMember(member);
        List<PostGetResponseDto> responseDtoList = new ArrayList<>();

        for (Post posts: postList) {
            PostGetResponseDto responseDto= new PostGetResponseDto(
                    posts.getCreatedAt(),
                    posts.getMember().getNickname(),
                    posts.getPostId(),
                    postLikeRepository.countByPost(posts),
                    posts.getTitle(),
                    posts.getContents()
            );
            responseDtoList.add(responseDto);
        }

        return ResponseDto.success(responseDtoList);

    }

    @Transactional
    public ResponseDto<?> getCommentPage(Long memberId) {

        Member member = isPresentMember(memberId);

        List<Comment> comment = commentRepository.findByMember(member);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comments: comment) {
            CommentResponseDto responseDto= new CommentResponseDto(
                    comments.getCommentId(),
                    comments.getComment(),
                    comments.getMember().getNickname(),
                    comments.getCreatedAt()
            );
            responseDtoList.add(responseDto);
        }
        return ResponseDto.success(responseDtoList);

    }


    @Transactional(readOnly = true)
    public Member isPresentMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElse(null);
    }


}
