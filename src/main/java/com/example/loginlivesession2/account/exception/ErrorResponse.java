package com.example.loginlivesession2.account.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter @Builder
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;


    // 객체 생성없이 클래스.method형식으로 부를려고 static을 이용한건가?
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }
}
