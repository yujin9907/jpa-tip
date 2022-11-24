package com.mtcoding.demo.config.auth;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mtcoding.demo.config.exception.CustomApiExceoption;
import com.mtcoding.demo.domain.user.User;
import com.mtcoding.demo.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    // 테스트코드에서 @s4로 하면 롬북 안 먹는 애러 때문에(테스트롬북 라이브러리의존하면 되긴함)
    private final Logger log = LoggerFactory.getLogger(getClass()); // 임포트 org.sl4 주의

    @Autowired
    private UserRepository userRepository;

    // jsp에선 sec:authorize access="isAuthenticated()" 인증 되면 접근할 수 있다는 것

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("디버그: " + "loadUserByUsername 실행됨");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomApiExceoption("아이디를 찾을 수 없음", HttpStatus.BAD_REQUEST));
        // return user.isPresent() ? new LoginUser(user.get()) : null;
        return new LoginUser(user);
    }
}