package com.hanhea.jwtlike.account.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
}
