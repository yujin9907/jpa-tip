package com.mtcoding.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mtcoding.demo.domain.user.User;
import com.mtcoding.demo.domain.user.UserRepository;
import com.mtcoding.demo.dto.UserReqDto.JoinReqDto;
import com.mtcoding.demo.dto.UserRespDto.joinRepsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true) // 레이지 로딩을 위해, select에서의 설정(readonly)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional // 스프링 트랜잭셔널 임포트 주의
    public joinRepsDto 회원가입(JoinReqDto joinReqDto) {

        log.debug("디버그 : 회원가입 시작");

        // 시큐리티는 무조건 해시된 비밀번호로 인증
        // 1. 비밀번호 암호화 => 따로 메서드를 빼도 됨. 나둬도 되고
        String rawPassword = joinReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        joinReqDto.setPassword(encPassword);

        // 2. 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());

        // 3. dto 응답
        return new joinRepsDto(userPS);
    }

}
