//package com.example.loginlivesession2.domain;
//
//import com.example.loginlivesession2.dto.request.MemberReqDto;
//import com.example.loginlivesession2.dto.request.PostReqDto;
//import com.example.loginlivesession2.dto.response.MemberResDto;
//import com.example.loginlivesession2.dto.response.PostResDto;
//import com.example.loginlivesession2.service.MemberService;
//import com.example.loginlivesession2.service.PostService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//class MemberTest {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private PostService postService;
//
//
//    @Test
//    @DisplayName("정상")
//    void createMember_Normal() {
//        String nickname = "공은희";
//        String pw = "5509kong";
//
//        MemberResDto member1 =  memberService.signup(new MemberReqDto(nickname,pw,pw)).getData();
//
//        Member member = memberService.isPresentMember(member1.getNickname());
//
//        String title = "제목입니다";
//        String content = "내용입니다";
//
//        PostReqDto postReqDto = new PostReqDto(title,content);
//
//        PostResDto post1 = postService.generatePost(member.getMemberid(), postReqDto).getData();
//        System.out.println(post1.getTitle());
//        System.out.println(member.getMemberid());
//        System.out.println(member.getNickname());
//        System.out.println(member.getPw());
//        assertEquals(post1.getTitle(),title);
//    }
//
//
//}