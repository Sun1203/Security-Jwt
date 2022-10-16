package com.example.loginlivesession2.account.service;

import com.example.loginlivesession2.account.dto.request.MemberReqDto;
import com.example.loginlivesession2.account.dto.response.MemberResponseDto;
import com.example.loginlivesession2.account.dto.response.ResponseDto;
import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.entity.RefreshToken;
import com.example.loginlivesession2.account.repository.MemberRepository;
import com.example.loginlivesession2.account.repository.RefreshTokenRepository;
import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.jwt.dto.TokenDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<?> signup(MemberReqDto memberReqDto) {
        // email 중복 검사
        if(memberRepository.findByNickname(memberReqDto.getNickname()).isPresent()){
            return ResponseDto.fail("OVERLAP_MEMBER", "중복된 사용자가 존재합니다.");
        }

        // pw와 pwck같은지 확인
        if (!memberReqDto.getPw().equals(memberReqDto.getPwck())) {
            return ResponseDto.fail("PASSWORD_NOT_EQUALS", "비밀번호가 같지 않습니다.");
        }

        // 비밀번호 암호화
        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPw()));
        Member member = new Member(memberReqDto);
        memberRepository.save(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getNickname());

        return ResponseDto.success(memberResponseDto);
    }

    @Transactional
    public ResponseDto<?> login(MemberReqDto memberReqDto, HttpServletResponse response) throws Exception {

        // throw할때 에러가 어떤종류 인지 초기화 그리고 날아갈때 잡아서 처리

        // 아이디 체크
        Member member = isPresentMember(memberReqDto.getNickname());
        if (member == null) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 체크
        if(!passwordEncoder.matches(memberReqDto.getPw(), member.getPw())) {
            return ResponseDto.fail("Not matches Password", "비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(memberReqDto.getNickname());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountNickname(memberReqDto.getNickname());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), memberReqDto.getNickname());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getNickname());

        return ResponseDto.success(memberResponseDto);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }
}
