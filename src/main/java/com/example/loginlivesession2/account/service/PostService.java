package com.example.loginlivesession2.account.service;

import com.example.loginlivesession2.account.dto.request.PostRequestDto;
import com.example.loginlivesession2.account.dto.response.*;
import com.example.loginlivesession2.account.entity.Comment;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import com.example.loginlivesession2.account.repository.CommentRepository;
import com.example.loginlivesession2.account.repository.MemberRepository;
import com.example.loginlivesession2.account.repository.PostLikeRepository;
import com.example.loginlivesession2.account.repository.PostRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRespoitory postRespoitory;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, Long memberId) {

        Member member = isPresentMember(memberId);
        if (member == null) return ResponseDto.fail("INVALID_TOKEN", "토큰이 유효하지 않습니다.");

        Post post = new Post(requestDto, member);
        postRespoitory.save(post);

        List<CommentResponseDto> list = new ArrayList<>();

        PostResponseDto postResponseDto = new PostResponseDto(
                                         post.getCreatedAt(),
                                         post.getTitle(),
                                         post.getContents(),
                                         post.getMember().getNickname(),
                                         post.getPostId(),
                                         postLikeRepository.countByPost(post),
                                         list
                                        );

        return ResponseDto.success(postResponseDto);
    }

    @Transactional
    public ResponseDto<?> updatePost(PostRequestDto requestDto, Long memberId, Long postId) {

        Member member = isPresentMember(memberId);
        if (member == null) return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");

        Optional<Post> post = postRespoitory.findById(postId);
        if (post.isEmpty()) return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 입니다.");

        if (!post.get().getMember().getMemberId().equals(memberId))
            return ResponseDto.fail("Not matches ID", "해당 게시글 작성자만 수정할 수 있습니다");

        List<PostRequestDto> list = new ArrayList<>();

        for (PostRequestDto request : list) {
            if (request.getTitle().equals(null)) request.setTitle(post.get().getTitle());
            if (request.getContents().equals(null)) request.setContents(post.get().getContents());
        }


        List<Comment> commentList = commentRepository.findByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getCommentId(),
                    comment.getComment(),
                    comment.getMember().getNickname(),
                    comment.getCreatedAt()
            );
            commentResponseDtoList.add(commentResponseDto);
        }


        post.get().update(requestDto);


        PostResponseDto postResponseDto = new PostResponseDto(
                post.get().getCreatedAt(),
                post.get().getTitle(),
                post.get().getContents(),
                post.get().getMember().getNickname(),
                post.get().getPostId(),
                postLikeRepository.countByPost(post.get()),
                commentResponseDtoList
        );


        return ResponseDto.success(postResponseDto);
    }

    @Transactional
    public ResponseDto<?> deleteOne(Long postId, Long memberId) {

        Optional<Post> post = postRespoitory.findById(postId);
        if (post.isEmpty()) return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글입니다.");

        if (!post.get().getMember().getMemberId().equals(memberId))
            return ResponseDto.fail("NOT_MATCH_ID", "게시글 작성자만 삭제할 수 있습니다");

        postRespoitory.deleteById(postId);

        return ResponseDto.success("");

    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost() {
        List<Post> list = postRespoitory.findAll();
        List<PostGetResponseDto> postList = new ArrayList<>();

        for (int i=0;i<list.size();i++) {
            PostGetResponseDto responseDto = new PostGetResponseDto(
                    list.get(i).getCreatedAt(),
                    list.get(i).getMember().getNickname(),
                    list.get(i).getPostId(),
                    postLikeRepository.countByPost(list.get(i)),
                    list.get(i).getTitle(),
                    list.get(i).getContents()
            );
            postList.add(responseDto);
        }

        return ResponseDto.success(postList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getOnePost(Long id) {
        Optional<Post> post = postRespoitory.findById(id);
        if (post.isEmpty()) return ResponseDto.fail("NOT_FOUND", "해당 게시물이 존재하지 않습니다");


        List<Comment> commentList = commentRepository.findByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getCommentId(),
                    comment.getComment(),
                    comment.getMember().getNickname(),
                    comment.getCreatedAt()
            );
            commentResponseDtoList.add(commentResponseDto);
        }

        PostResponseDto postResponseDto = new PostResponseDto(
                post.get().getCreatedAt(),
                post.get().getTitle(),
                post.get().getContents(),
                post.get().getMember().getNickname(),
                post.get().getPostId(),
                postLikeRepository.countByPost(post.get()),
                commentResponseDtoList
        );

        return ResponseDto.success(postResponseDto);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElse(null);
    }

}
