package com.example.loginlivesession2.domain;

import com.example.loginlivesession2.dto.request.MemberReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends Timestamped {

    @Id
    @Column(name = "memberid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String pw;

    public Member(MemberReqDto memberReqDto) {
        this.nickname = memberReqDto.getNickname();
        this.pw = memberReqDto.getPw();
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.pw);
    }

}
