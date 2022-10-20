package com.hanhea.jwtlike.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanhea.jwtlike.account.dto.Request.AccountsignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="members")
public class Account extends Timestamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public Account(Account account){
        this.nickname = account.getNickname();
        this.password = account.getPassword();
    }

    public Account(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }

    public Account(AccountsignupDto accountsignupDto){
        this.nickname = accountsignupDto.getNickname();
        this.password = accountsignupDto.getPassword();
    }
}
