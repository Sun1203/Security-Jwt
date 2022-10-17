package com.hanhea.jwtlike.account.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CommonResponseDto <T> {
    private String success;
    private T data;
    private int statecode;

    public CommonResponseDto(String success, T data, int error){
        this.success = success;
        this.data = data;
        this.statecode = error;
    }
}
