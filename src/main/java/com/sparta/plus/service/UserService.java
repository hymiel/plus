package com.sparta.plus.service;


import com.sparta.plus.dto.*;
import com.sparta.plus.entity.User;
import com.sparta.plus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    // 닉네임, 비밀번호, 비밀번호 확인을 request 에서전달받기
    // 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
    // 비밀번호 확인은 비밀번호와 정확하게 일치하기
    // 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response 에 포함하기
    @Transactional
    public ResponseEntity<ApiResponseDto> Signup(SignupRequestDto signupRequestDto) {
        String nickName = signupRequestDto.getNickName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
       String rePassword = passwordEncoder.encode(signupRequestDto.getRePassword());

        // 유저 닉네임 중복확인
        if (userRepository.findByNickName(nickName).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요.");
            return ResponseEntity.status(400).body(new ApiResponseDto("이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요.",HttpStatus.BAD_REQUEST.value()));
        }
        // 비밀번호 2번확인
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getRePassword())) {
//            throw new IllegalArgumentException("입력하신 비밀번호가 다릅니다. 비밀번호 일치여부를 확인해주세요.");
            return ResponseEntity.status(400).body(new ApiResponseDto("입력하신 비밀번호가 다릅니다. 비밀번호 일치여부를 확인해주세요.",HttpStatus.BAD_REQUEST.value()));
        }
        // 닉네임과 비밀번호를 비교하여 같은 값이 포함되어 있으면 회원가입 실패
        if (signupRequestDto.getNickName().contains(signupRequestDto.getPassword())){
//            throw new IllegalArgumentException("비밀번호에는 닉네임을 사용할 수 없습니다.");
            return ResponseEntity.status(400).body(new ApiResponseDto("비밀번호에는 닉네임을 사용할 수 없습니다.",HttpStatus.BAD_REQUEST.value()));
        }
        User user = new User(nickName, password, rePassword);
        userRepository.save(user);
        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입이 완료 되었습니다.", HttpStatus.CREATED.value()));
}
    //로그인
    // 닉네임, 비밀번호를 request 에서전달받기
    // 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 response 에 포함하기
    // 로그인 성공 시 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기
    @Transactional
    public ResponseEntity<ApiResponseDto> signin(AuthRequestDto authRequestDto) {
        String nickName = authRequestDto.getNickName();
        String password = authRequestDto.getPassword();

        User user = userRepository.findByNickName(nickName).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(400).body(new ApiResponseDto("닉네임 또는 패스워드를 확인해주세요.", HttpStatus.BAD_REQUEST.value()));
        } else {
            return ResponseEntity.status(201).body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
        }
    }
}

