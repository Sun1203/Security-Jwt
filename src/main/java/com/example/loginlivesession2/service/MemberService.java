package com.example.loginlivesession2.service;

import com.example.loginlivesession2.dto.request.MemberReqDto;
import com.example.loginlivesession2.dto.request.LoginReqDto;
import com.example.loginlivesession2.dto.response.MemberResDto;
import com.example.loginlivesession2.domain.Member;
import com.example.loginlivesession2.domain.RefreshToken;
import com.example.loginlivesession2.repository.MemberRepository;
import com.example.loginlivesession2.repository.RefreshTokenRepository;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
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
    public GlobalResDto<?> signup(MemberReqDto memberReqDto) {
        // email 중복 검사
        if (null != isPresentMember(memberReqDto.getNickname())) {
            return GlobalResDto.fail("DUPLICATED_NICKNAME", "이미 사용중인 닉네임입니다.");
        }

        if (!memberReqDto.getPw().equals(memberReqDto.getPwck())) {
            return GlobalResDto.fail("PASSWORDS_NOT_MATCHED", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPw()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return GlobalResDto.success(new MemberResDto(member.getNickname()));
    }

    @Transactional
    public GlobalResDto<?> login(LoginReqDto loginReqDto, HttpServletResponse response) {

        Member member = isPresentMember(loginReqDto.getNickname());
        if (member == null) {
            return GlobalResDto.fail("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다.");
        }

        if (!member.validatePassword(passwordEncoder, loginReqDto.getPw())) {
            return GlobalResDto.fail("WRONG_PASSWORD", "비밀번호가 틀렸습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getNickname());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountNickname(loginReqDto.getNickname());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getNickname());
            refreshTokenRepository.save(newToken);
        }

        MemberResDto memberResDto = new MemberResDto(member.getNickname());

        setHeader(response, tokenDto);

        return GlobalResDto.success(memberResDto);

    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        return member.orElse(null);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
