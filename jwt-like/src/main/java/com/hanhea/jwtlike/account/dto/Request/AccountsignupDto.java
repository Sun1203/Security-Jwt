package com.hanhea.jwtlike.account.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountsignupDto {
    @NotBlank(message = "아이디를 입력하세요")
    @Size(min=4,max=12,message = "아이디의 길이를 확인하세요")
    @Pattern(regexp = "^(?=.*[A-Za-z.*\\d])[A-Za-z\\d]{4,12}$", message = "아이디 구성을 확인하세요")
    private String nickname;

    @NotBlank(message = "패스워드를 입력하세요")
    @Size(min=8,max=20,message = "패스워드 길이를 확인하세요")
    @Pattern(regexp = "^(?=.*[A-Za-z.*\\d])[A-Za-z\\d]{8,16}$", message = "패스워드 구성을 확인하세요")
    private String password;

    @NotBlank(message = "확인 패스워드를 입력하세요")
    private String passwordConfirm;

    public boolean Accountcheck(){
        return this.password.equals(this.passwordConfirm);
    }
}
