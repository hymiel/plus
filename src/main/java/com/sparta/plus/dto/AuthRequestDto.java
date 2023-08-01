package com.sparta.plus.dto;

import com.sparta.plus.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    // 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    @NotBlank(message = "닉네임은 필수입력입니다.")
    private String nickName;

    // 비밀번호는 최소 4자 이상
    @NotBlank(message = "비밀번호는 필수입력입니다.")
    private String password;

    private UserRoleEnum role; // 회원 권한 (admin, user)
}
