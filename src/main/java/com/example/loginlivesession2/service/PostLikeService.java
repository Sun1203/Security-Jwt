package com.example.loginlivesession2.service;

import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import com.example.loginlivesession2.domain.PostLIke;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.response.PostLikeResDto;
import com.example.loginlivesession2.repository.PostLikeRepository;
import com.example.loginlivesession2.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public GlobalResDto<PostLikeResDto> plusLike(UserDetailsImpl userDetails, Long postid) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }

        PostLIke postLIke = isPresentLike(post, userDetails.getAccount());

        if (postLIke != null) {
            return GlobalResDto.fail("FAIL_LIKE", "좋아요는 한번만 가능합니다");
        }
        postLikeRepository.save(new PostLIke(userDetails.getAccount(), post));
        post.setPostlike(1);
        postRepository.save(post);
        return GlobalResDto.success(new PostLikeResDto("좋아요"));
    }

    @Transactional
    public GlobalResDto<PostLikeResDto> delLike(UserDetailsImpl userDetails, Long postid) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }

        PostLIke postLIke = isPresentLike(post, userDetails.getAccount());

        if(postLIke==null){
            return GlobalResDto.fail("FAIL_CANCEL_LIKE","좋아요 취소는 한번만 가능합니다");
        }

        postLikeRepository.delete(postLIke);
        post.setPostlike(-1);
        postRepository.save(post);
        return GlobalResDto.success(new PostLikeResDto("좋아요 취소"));
    }

    public Post isPresentPost(Long postid) {
        Optional<Post> post = postRepository.findById(postid);
        return post.orElse(null);
    }

    public PostLIke isPresentLike(Post post, Member member) {
        Optional<PostLIke> postLIke = postLikeRepository.findAllByPostAndMember(post, member);
        return postLIke.orElse(null);
    }
}