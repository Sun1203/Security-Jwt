package com.hanhea.jwtlike.account.dto.Request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountloginDto {
    @NotBlank(message = "아이디를 입력하세요")
    private String nickname;

    @NotBlank(message = "패스워드를 입력하세요")
    private String password;
}
