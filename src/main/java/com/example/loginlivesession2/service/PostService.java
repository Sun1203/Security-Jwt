package com.example.loginlivesession2.service;

import com.example.loginlivesession2.domain.Comment;
import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.Post;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.request.PostReqDto;
import com.example.loginlivesession2.dto.response.CommentResDto;
import com.example.loginlivesession2.dto.response.PostAllResDto;
import com.example.loginlivesession2.dto.response.PostResDto;
import com.example.loginlivesession2.dto.response.delDto;
import com.example.loginlivesession2.repository.CommentRepository;
import com.example.loginlivesession2.repository.MemberRepository;
import com.example.loginlivesession2.repository.PostLikeRepository;
import com.example.loginlivesession2.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public GlobalResDto<PostResDto> generatePost(Long memberid, PostReqDto postReqDto) {
        Member member = isPresentMember(memberid);
        if(member==null){
            return GlobalResDto.fail("MEMBER_NOT_FOUND","사용자가 존재하지 않습니다.");
        }
        Post post = new Post(member, postReqDto);
        postRepository.save(post);
        PostResDto postResDto = PostResDto.toPostResDto(post, null);
        return GlobalResDto.success(postResDto);
    }

    @Transactional
    public GlobalResDto<PostResDto> updatePost(UserDetailsImpl userDetails, PostReqDto postReqDto, Long postid) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        if (!userDetails.getAccount().getMemberid().equals(post.getMember().getMemberid())) {
            return GlobalResDto.fail("NO_PERMISSION", "게시물은 자신이 작성한 게시물만 수정할 수 있습니다.");
        }
        post.update(postReqDto);
        List<Comment> comments = isPresentComment(post);
        List<CommentResDto> commentResDtos = commentResDtoList(comments);
        PostResDto postResDto = PostResDto.toPostResDto(post, commentResDtos);
        return GlobalResDto.success(postResDto);
    }

    public GlobalResDto<List<PostAllResDto>> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostAllResDto> postAllResDtos = new ArrayList<>();
        for (Post post : posts) {
            PostAllResDto postAllResDto = PostAllResDto.toPostResDto(post);
            postAllResDtos.add(postAllResDto);
        }
        return GlobalResDto.success(postAllResDtos);
    }

    public GlobalResDto<PostResDto> getPost(Long postid) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        List<Comment> comments = isPresentComment(post);
        List<CommentResDto> commentResDtos = commentResDtoList(comments);
        PostResDto postResDto = PostResDto.toPostResDto(post, commentResDtos);
        return GlobalResDto.success(postResDto);
    }

    @Transactional
    public GlobalResDto<delDto> delPost(Long postid, UserDetailsImpl userDetails) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        if (!userDetails.getAccount().getMemberid().equals(post.getMember().getMemberid())) {
            return GlobalResDto.fail("NO_PERMISSION", "게시물은 자신이 작성한 게시물만 삭제할 수 있습니다.");
        }
        List<Comment> comments = isPresentComment(post);
        commentRepository.deleteAll(comments);
        postLikeRepository.deleteBypost(post);
        postRepository.delete(post);
        //댓글삭제도 같이하자
        return GlobalResDto.success(new delDto("삭제 완료"));
    }

    public Member isPresentMember(Long memberid){
        Optional<Member> member = memberRepository.findById(memberid);
        return member.orElse(null);
    }

    public Post isPresentPost(Long postid) {
        Optional<Post> post = postRepository.findById(postid);
        return post.orElse(null);
    }

    public List<Comment> isPresentComment(Post post) {
        Optional<List<Comment>> comments = commentRepository.findAllByPost(post);
        return comments.orElse(null);
    }

    public List<CommentResDto> commentResDtoList(List<Comment> comments) {
        List<CommentResDto> commentResDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResDto commentResDto = CommentResDto.toCommentResDto(comment);
            commentResDtos.add(commentResDto);
        }
        return commentResDtos;
    }
}
