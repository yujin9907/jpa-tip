package com.mtcoding.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mtcoding.demo.config.enums.UserEnum;
import com.mtcoding.demo.handler.LoginHandler;

// securityFilterChain

@Configurable
public class SecurityConfig {

    // 생성자 주입 금지 : 순환 참조 ... 복잡한 문제가 생김 => 오토와이어드 사용함
    @Autowired // 생성자 주입이 아닌, 오토와이어드 어노테이션을 보고 넣어줌-> 리플렉션
    private LoginHandler loginHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }; // 시큐리티 로그인 인증 위해선 무조건 해시 비밀번호 필요

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable(); // 해더에 원래 깔린 시큐리티 제거, 실제 적용시엔 지우기
        http.csrf().disable(); // 포스트맨 요청 테스트를 위해서, 실제 적용시엔 able

        // 주소 설계 추후 고민
        http.authorizeHttpRequests()
                .antMatchers("/api/transaction/**").authenticated()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/account/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(UserEnum.ADMIN.toString())
                .anyRequest().permitAll()
                .and()
                .formLogin() // x-www-form-urlencoded (post)
                // .usernameParameter("user") // 디폴트 : username
                // .passwordParameter("pw") // 디폴트 : password
                .loginProcessingUrl("/api/login")
                .successHandler(loginHandler)
                .failureHandler(loginHandler);

        return http.build();
    }
}
