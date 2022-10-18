package com.example.loginlivesession2.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z.*\\d])[A-Za-z\\d]{4,12}$", message = "id오류")
    private String nickname;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호 오류")
    private String pw;

    private String pwck;

    public MemberReqDto(String nickname, String pw, String pwck) {
        this.nickname = nickname;
        this.pw = pw;
        this.pwck = pwck;
    }

    public void setEncodePwd(String encodePwd) {
        this.pw = encodePwd;
    }

}
