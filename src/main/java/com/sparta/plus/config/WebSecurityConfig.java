package com.sparta.plus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class WebSecurityConfig {

    @Bean //BCryptPasswordEncoder 를 Bean 으로 등록하여 비밀번호를 암호화하는 데 사용
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
