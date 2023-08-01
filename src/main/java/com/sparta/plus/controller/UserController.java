package com.sparta.plus.controller;

import com.sparta.plus.dto.ApiResponseDto;
import com.sparta.plus.dto.AuthRequestDto;
import com.sparta.plus.dto.SignupRequestDto;
import com.sparta.plus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

        private final UserService userService;

        // 회원가입
        @PostMapping("auth/signup")
        public ResponseEntity<ApiResponseDto> signup (@RequestBody SignupRequestDto signupRequestDto) {
            return userService.Signup(signupRequestDto);
        }
        // 로그인
        @PostMapping("signin")
        public ResponseEntity<ApiResponseDto> signin(@RequestBody AuthRequestDto authRequestDto) {
                return userService.signin(authRequestDto);
        }
}
