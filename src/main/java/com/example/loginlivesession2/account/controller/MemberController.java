package com.example.loginlivesession2.account.controller;

import com.example.loginlivesession2.account.dto.request.MemberReqDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.service.MemberService;
import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {


    private final JwtUtil jwtUtil;
    private final MemberService memberService;


    @PostMapping(value = "/member/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberReqDto memberReqDto) {
        return memberService.signup(memberReqDto);
    }

    @PostMapping(value = "/member/login")
    public ResponseDto<?> login(@RequestBody @Valid MemberReqDto memberReqDto, HttpServletResponse response) throws Exception {
        return memberService.login(memberReqDto, response);
    }

    @GetMapping(value = "/issue/token")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getNickname(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }

}