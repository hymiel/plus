package com.sparta.plus.controller;

import com.sparta.plus.dto.ApiResponseDto;
import com.sparta.plus.dto.SignupRequestDto;
import com.sparta.plus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
        private UserService userService;

        // 회원가입
        @PostMapping("auth/signup")
        public ResponseEntity<ApiResponseDto> signup (@RequestBody SignupRequestDto signupRequestDto) {
            try {
                userService.Signup(signupRequestDto);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new ApiResponseDto("이미 존재하는 닉네임 입니다. 다른 닉네임을 입력해 주세요", HttpStatus.BAD_REQUEST.value()));
            }
            return ResponseEntity.status(201).body(new ApiResponseDto("회원가입이 완료 되었습니다.", HttpStatus.CREATED.value()));
        }

        // 로그인
//        @PostMapping("signin")

}
