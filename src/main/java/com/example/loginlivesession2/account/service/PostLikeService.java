package com.example.loginlivesession2.account.service;


import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import com.example.loginlivesession2.account.entity.PostLike;
import com.example.loginlivesession2.account.repository.MemberRepository;
import com.example.loginlivesession2.account.repository.PostLikeRepository;
import com.example.loginlivesession2.account.repository.PostRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRespoitory postRespoitory;


    @Transactional
    public ResponseDto<?> createPostLike(Long postId, Long memberId) {

        Post post = isPresentPost(postId);
        if (post == null) return ResponseDto.fail("FOUND_NOT_POST", "게시글을 찾을 수 없습니다.");

        Member member = isPresentMember(memberId);

        PostLike postLike = new PostLike(member, post);
        if (postLikeRepository.existsByMemberAndPost(member, post))
            return ResponseDto.fail("OVERLAP_LIKED", "좋아요는 한번만 가능합니다.");

        postLikeRepository.save(postLike);


        return ResponseDto.success("좋아요 성공");
    }

    @Transactional
    public ResponseDto<?> deletePostLike(Long postId, Long memberId) {
        Post post = isPresentPost(postId);
        if (post == null) return ResponseDto.fail("FOUND_NOT_POST", "게시글을 찾을 수 없습니다.");

        Member member = isPresentMember(memberId);

        if (postLikeRepository.deleteByMemberAndPost(member, post) == 0)
            return ResponseDto.fail("ALREADY_CANCEL_LIKE", "이미 좋아요를 취소 하셨습니다.");
        return ResponseDto.success("좋아요 삭제 완료");


    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long postId) {
        Optional<Post> optionalMember = postRespoitory.findById(postId);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElse(null);
    }

}
