package com.sparta.plus.controller;

import com.sparta.plus.dto.ApiResponseDto;
import com.sparta.plus.dto.AuthRequestDto;
import com.sparta.plus.dto.SignupRequestDto;
import com.sparta.plus.jwt.JwtUtil;
import com.sparta.plus.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

        private final UserService userService;
        private final JwtUtil jwtUtil;

        // 회원가입
        @PostMapping("auth/signup")
        public ResponseEntity<ApiResponseDto> signup (@RequestBody SignupRequestDto signupRequestDto) {
            return userService.Signup(signupRequestDto);
        }
        // 로그인
        @PostMapping("/auth/signin")
        public ResponseEntity<ApiResponseDto> signin(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
                // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(authRequestDto.getNickName(), authRequestDto.getRole()));
                return userService.signin(authRequestDto);
        }
}
