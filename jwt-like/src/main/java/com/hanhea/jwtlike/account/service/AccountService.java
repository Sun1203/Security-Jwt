package com.hanhea.jwtlike.account.service;

import com.hanhea.jwtlike.account.dto.Request.AccountloginDto;
import com.hanhea.jwtlike.account.dto.Request.AccountsignupDto;
import com.hanhea.jwtlike.account.dto.Response.CommonResponseDto;
import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.repository.AccountRepository;
import com.hanhea.jwtlike.jwt.jwt_utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final JWTUtil jwtutil;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity signup(AccountsignupDto signupDto) {

        try {
            if(accountRepository.findAccountByNickname(signupDto.getNickname()).isPresent())    throw new RuntimeException("아이디 중복");
            //비번 일치하지 않으면
            if (!signupDto.Accountcheck()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new CommonResponseDto("fail", "패스워드 재확인", 400));
            } else {
                Account account = new Account(signupDto.getNickname(), passwordEncoder.encode(signupDto.getPassword()));
                accountRepository.save(account);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new CommonResponseDto("success", account.getNickname(), 200));
            }
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail", ex.getMessage(), 400));
        }
    }

    @Transactional
    public ResponseEntity login(AccountloginDto accountloginDto, HttpServletResponse response){
        try {
            Account account = accountRepository.findAccountByNickname(accountloginDto.getNickname()).orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다."));
            if(!passwordEncoder.matches(accountloginDto.getPassword(), account.getPassword()))  throw new RuntimeException("패스워드가 틀립니다.");
            String token = jwtutil.createAllToken(account.getNickname());//ACTK나옴, RFTK redis 저장
            response.addHeader(JWTUtil.ACCESS_TOKEN, token);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommonResponseDto("success", accountloginDto.getNickname(), 200));
        } catch (RuntimeException ex){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponseDto("fail", ex.getMessage(), 400));
        }
    }
}