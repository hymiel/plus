package com.sparta.plus.exception;

import com.sparta.plus.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TokenValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenValidationException.class)
    protected ResponseEntity<Object> handleTokenValidationException(TokenValidationException ex) {
        ApiResponseDto responseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
