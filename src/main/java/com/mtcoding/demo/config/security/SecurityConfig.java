package com.mtcoding.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mtcoding.demo.handler.CustomLoginHandler;

// securityFilterChain

@Configuration // 미친놈 이거 configurable로 잘못함
// @EnableWebSecurity // 이거 없으면 나는 작동을 안 함... => 없어도 됨
public class SecurityConfig {

    // 생성자 주입 금지 : 순환 참조 ... 복잡한 문제가 생김 => 오토와이어드 사용함
    @Autowired // 생성자 주입이 아닌, 오토와이어드 어노테이션을 보고 넣어줌-> 리플렉션
    private CustomLoginHandler customLoginHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }; // 시큐리티 로그인 인증 위해선 무조건 해시 비밀번호 필요

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http.headers().frameOptions().disable(); // 해더에 원래 깔린 시큐리티 제거, 실제 적용시엔 지우기
    // http.csrf().disable(); // 포스트맨 요청 테스트를 위해서, 실제 적용시엔 able

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/transaction/**").authenticated()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/account/**").authenticated()
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler(customLoginHandler)
                .failureHandler(customLoginHandler);
        return http.build();
    }
}
