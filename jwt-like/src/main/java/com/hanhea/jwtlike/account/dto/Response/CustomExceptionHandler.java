package com.hanhea.jwtlike.account.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hanhea.jwtlike.account.entity.Error;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {

        List<Error> errors = new ArrayList<>();

        for(FieldError field : exception.getBindingResult().getFieldErrors())
            errors.add(new Error(field.getField(), field.getDefaultMessage()));

        CommonResponseDto commonResponseDto = new CommonResponseDto("fail", errors, 400);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(commonResponseDto);
    }
}