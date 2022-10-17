package com.example.loginlivesession2.controller.exceptionHandler;

import com.example.loginlivesession2.dto.response.Error;
import com.example.loginlivesession2.dto.globalDto.GlobalResDto;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalResDto<?> handleValidationExceptions(MethodArgumentNotValidException exception) {

        List<Error> errors = new ArrayList<>();

        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            errors.add(new Error(field.getField(), field.getDefaultMessage()));
        }

        return GlobalResDto.fail(errors.get(0).getCode().toUpperCase() + "_ERROR", errors.get(0).getMessage());
    }
}