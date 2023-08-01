package com.sparta.plus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDto {
    // 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    @NotBlank(message = "닉네임은 필수입력입니다.")
    private String nickName;

    // 비밀번호는 최소 4자 이상
    @NotBlank(message = "비밀번호는 필수입력입니다.")
    private String password;
}
