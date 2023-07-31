package com.sparta.plus.service;


import com.sparta.plus.dto.SignupRequestDto;
import com.sparta.plus.entity.User;
import com.sparta.plus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void Signup(SignupRequestDto signupRequestDto) {
        String nickName = signupRequestDto.getNickName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
//        String rePassword = passwordEncoder.encode(signupRequestDto.getRepassword());

        // 유저 닉네임 중복확인
        if (userRepository.findByNickname(nickName).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요.");
        }
        // 비밀번호 2번확인
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getRePassword())) {
            throw new IllegalArgumentException("입력하신 비밀번호가 다릅니다. 비밀번호 일치여부를 확인해주세요.");
        }

        // 닉네임과 비밀번호를 비교하여 같은 값이 포함되어 있으면 회원가입 실패
        if (signupRequestDto.getNickName().contains(signupRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호에는 닉네임을 사용할 수 없습니다.");
        }
        User user = new User(nickName, password);
        userRepository.save(user);
    }

    //로그인
    // 닉네임, 비밀번호를 request 에서전달받기
    // 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 response 에 포함하기
    // 로그인 성공 시 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기

}
