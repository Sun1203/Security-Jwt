package com.example.loginlivesession2.dto.globalDto;

import com.example.loginlivesession2.dto.response.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResDto<T> {

    private boolean success;
    private T data;
    private Error error;


    public static <T> GlobalResDto<T> success(T data) {
        return new GlobalResDto<>(true, data, null);
    }

    public static <T> GlobalResDto<T> fail(String code, String meg) {
        return new GlobalResDto<>(false, null, new Error(code, meg));
    }
}
