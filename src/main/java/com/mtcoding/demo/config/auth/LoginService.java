package com.mtcoding.demo.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mtcoding.demo.domain.user.User;
import com.mtcoding.demo.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;

    // jsp에선 sec:authorize access="isAuthenticated()" 인증 되면 접근할 수 있다는 것

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? new LoginUser(user.get()) : null;
    }
}