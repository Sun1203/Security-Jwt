package com.example.loginlivesession2.account.service;

import com.example.loginlivesession2.account.dto.request.CommentRequestDto;
import com.example.loginlivesession2.account.dto.response.CommentResponseDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.entity.Comment;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.Post;
import com.example.loginlivesession2.account.repository.CommentRepository;
import com.example.loginlivesession2.account.repository.MemberRepository;
import com.example.loginlivesession2.account.repository.PostRespoitory;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRespoitory postRespoitory;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long postId, Long memberId) {
        Optional<Post> post = postRespoitory.findById(postId);
        if (post.isEmpty()) return ResponseDto.fail("POST_NOT_FOUND", "게시물을 찾을수 없습니다");

        Optional<Member> member = memberRepository.findById(memberId);

        Comment comment = new Comment(commentRequestDto, post.get(), member.get());
        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(
                comment.getCommentId(),
                comment.getComment(),
                comment.getMember().getNickname(),
                comment.getCreatedAt()
        );

        return ResponseDto.success(commentResponseDto);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long postId, Long commentId, Long memberId, CommentRequestDto commentRequestDto) {

        Optional<Post> post = postRespoitory.findById(postId);
        if (post.isEmpty()) return ResponseDto.fail("POST_NOT_FOUND", "해당 게시글이 존재하지 않습니다");

        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) return ResponseDto.fail("COMMENT_NOT_FOUND", "해당 댓글이 존재하지 않습니다");

        if (!memberId.equals(comment.get().getMember().getMemberId()))
            return ResponseDto.fail("NOT_MATCH_ID", "해당 댓글 작성자만 수정할 수 있습니다");

        comment.get().update(commentRequestDto);
        commentRepository.save(comment.get());

        CommentResponseDto commentResponseDto = new CommentResponseDto(
                comment.get().getCommentId(),
                comment.get().getComment(),
                comment.get().getMember().getNickname(),
                comment.get().getCreatedAt()
        );

        return ResponseDto.success(commentResponseDto);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long postId, Long commentId, Long memberId) {

        Optional<Post> post = postRespoitory.findById(postId);
        if (post.isEmpty()) return ResponseDto.fail("POST_NOT_FOUND", "해당 게시글이 존재하지 않습니다");

        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) return ResponseDto.fail("COMMENT_NOT_FOUND", "해당 댓글이 존재하지 않습니다");

        if (!memberId.equals(comment.get().getMember().getMemberId()))
            return ResponseDto.fail("NOT_MATCH_ID", "해당 댓글 작성자만 삭제할 수 있습니다");

        commentRepository.deleteById(commentId);

        return ResponseDto.success("");
    }
}
