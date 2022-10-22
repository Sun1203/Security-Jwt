package com.example.loginlivesession2.account.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저 id를 찾을 수 없습니다"),
    ;
    private final int httpStatus;
    private final String code;
    private final String message;
}
