package com.sparta.plus.security;

import com.sparta.plus.entity.User;
import com.sparta.plus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + nickName));

        return new UserDetailsImpl(user);
    }
}
