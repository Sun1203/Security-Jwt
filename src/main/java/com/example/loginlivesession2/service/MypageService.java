package com.example.loginlivesession2.service;

import com.example.loginlivesession2.domain.Comment;
import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import com.example.loginlivesession2.domain.PostLIke;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.response.CommentResDto;
import com.example.loginlivesession2.dto.response.PostAllResDto;
import com.example.loginlivesession2.repository.CommentRepository;
import com.example.loginlivesession2.repository.PostLikeRepository;
import com.example.loginlivesession2.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    public GlobalResDto<?> getMypagePost(UserDetailsImpl userDetails) {
        List<Post> posts = isPresentPost(userDetails.getAccount());
        List<PostAllResDto> postAllResDtos = new ArrayList<>();

        for(Post post : posts){
            PostAllResDto postAllResDto = PostAllResDto.toPostResDto(post);
            postAllResDtos.add(postAllResDto);
        }

        return GlobalResDto.success(postAllResDtos);
    }

    public GlobalResDto<?> getMypageComment(UserDetailsImpl userDetails) {
        List<Comment> comments = isPresentComment(userDetails.getAccount());
        List<CommentResDto> commentResDtos = new ArrayList<>();

        for(Comment comment : comments){
            CommentResDto commentResDto = CommentResDto.toCommentResDto(comment);
            commentResDtos.add(commentResDto);
        }

        return GlobalResDto.success(commentResDtos);
    }

    public GlobalResDto<?> getMypagePostLike(UserDetailsImpl userDetails) {
        List<PostLIke> postLIkes = isPresentPostLike(userDetails.getAccount());

        List<Post> posts = new ArrayList<>();
        for(PostLIke postLIke : postLIkes){
            posts.add(postLIke.getPost());
        }

        List<PostAllResDto> postAllResDtos = new ArrayList<>();
        for(Post post : posts){
            PostAllResDto postAllResDto = PostAllResDto.toPostResDto(post);
            postAllResDtos.add(postAllResDto);
        }

        return GlobalResDto.success(postAllResDtos);
    }

    public List<Post> isPresentPost(Member member){
        Optional<List<Post>> posts = postRepository.findByMember(member);
        return posts.orElse(null);
    }

    public List<Comment> isPresentComment(Member member){
        Optional<List<Comment>> comments = commentRepository.findByMember(member);
        return comments.orElse(null);
    }

    public List<PostLIke> isPresentPostLike(Member member){
        Optional<List<PostLIke>> postLIkes = postLikeRepository.findByMember(member);
        return postLIkes.orElse(null);
    }

}
