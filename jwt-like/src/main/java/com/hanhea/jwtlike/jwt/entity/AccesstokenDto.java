package com.hanhea.jwtlike.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccesstokenDto {
    @NotBlank(message = "{token.notblank}")
    private String accessToken;
}
