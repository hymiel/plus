package com.sparta.plus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequestDto {

    // 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    @NotBlank(message = "닉네임은 필수입력입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$",
            message = "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
    private String nickName;

    // 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
    @NotBlank(message = "비밀번호는 최소 4자 이상이며, 닉네임과 비슷한 값을 넣을 수 없습니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상입니다.")
    private String password;

    @Size(min = 4, message = "입력한 비밀번호와 같은 비밀번호를 입력해주세요.")
    private String rePassword;

    private String authKey;
}
