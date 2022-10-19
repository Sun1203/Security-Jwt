package com.example.loginlivesession2.service;

import com.example.loginlivesession2.domain.Comment;
import com.example.loginlivesession2.domain.Post;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import com.example.loginlivesession2.dto.request.CommentReqDto;
import com.example.loginlivesession2.dto.response.CommentResDto;
import com.example.loginlivesession2.dto.response.delDto;
import com.example.loginlivesession2.repository.CommentRepository;
import com.example.loginlivesession2.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public GlobalResDto<CommentResDto> generateComment(UserDetailsImpl userDetails, Long postid, CommentReqDto commentReqDto) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        Comment comment = new Comment(userDetails, post, commentReqDto);
        commentRepository.save(comment);
        CommentResDto commentResDto = CommentResDto.toCommentResDto(comment);
        return GlobalResDto.success(commentResDto);
    }

    @Transactional
    public GlobalResDto<CommentResDto> updateComment(UserDetailsImpl userDetails, Long postid, Long commentid, CommentReqDto commentReqDto) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        Comment comment = isPresentComment(commentid);
        if (comment == null) {
            return GlobalResDto.fail("COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다");
        }
        if (!userDetails.getAccount().getMemberid().equals(comment.getMember().getMemberid())) {
            return GlobalResDto.fail("NO_PERMISSION", "게시물은 자신이 작성한 게시물만 수정할 수 있습니다.");
        }
        comment.update(commentReqDto);
        CommentResDto commentResDto = CommentResDto.toCommentResDto(comment);
        return GlobalResDto.success(commentResDto);
    }

    @Transactional
    public GlobalResDto<delDto> delComment(UserDetailsImpl userDetails, Long postid, Long commentid) {
        Post post = isPresentPost(postid);
        if (post == null) {
            return GlobalResDto.fail("POST_NOT_FOUND", "게시물이 존재하지 않습니다");
        }
        Comment comment = isPresentComment(commentid);
        if (comment == null) {
            return GlobalResDto.fail("COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다");
        }
        if (!userDetails.getAccount().getMemberid().equals(comment.getMember().getMemberid())) {
            return GlobalResDto.fail("NO_PERMISSION", "게시물은 자신이 작성한 게시물만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return GlobalResDto.success(new delDto("삭제 완료"));
    }

    public Post isPresentPost(Long postid) {
        Optional<Post> post = postRepository.findById(postid);
        return post.orElse(null);
    }

    public Comment isPresentComment(Long commentid) {
        Optional<Comment> comment = commentRepository.findById(commentid);
        return comment.orElse(null);
    }

}
