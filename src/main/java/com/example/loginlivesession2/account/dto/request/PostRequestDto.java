package com.example.loginlivesession2.account.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter @Setter
public class PostRequestDto {
    @NotBlank(message = "채워주세요")
    private String title;
    private String contents;
}
