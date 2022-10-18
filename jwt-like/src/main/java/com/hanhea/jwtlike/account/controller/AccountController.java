package com.hanhea.jwtlike.account.controller;

import com.hanhea.jwtlike.account.dto.Request.AccountloginDto;
import com.hanhea.jwtlike.account.dto.Request.AccountsignupDto;
import com.hanhea.jwtlike.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/member/signup")
    public ResponseEntity signup(@RequestBody @Valid AccountsignupDto accountsignupDto){
        return accountService.signup(accountsignupDto);
    }

    @PostMapping(value = "/member/login")
    public ResponseEntity login(@RequestBody @Valid AccountloginDto accountloginDto, HttpServletResponse response){
        return accountService.login(accountloginDto, response);
    }
}
