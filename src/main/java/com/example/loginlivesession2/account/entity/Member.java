package com.example.loginlivesession2.account.entity;

import com.example.loginlivesession2.account.dto.request.MemberReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @NotBlank
    private String nickname;
    @NotBlank
    private String pw;


    public Member(MemberReqDto memberReqDto) {
        this.nickname = memberReqDto.getNickname();
        this.pw = memberReqDto.getPw();
    }

}
