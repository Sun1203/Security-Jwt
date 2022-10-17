package com.example.loginlivesession2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank
    private String nickname;
    @NotBlank
    private String pw;

    public LoginReqDto(String nickname, String pw) {
        this.nickname = nickname;
        this.pw = pw;
    }

}

