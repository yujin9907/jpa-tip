package com.mtcoding.demo.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtcoding.demo.config.auth.LoginUser;
import com.mtcoding.demo.dto.UserReqDto.LoginReqDto;
import com.mtcoding.demo.handler.CustomLoginHandler;

// 원래 : 인증, userdetail service 실행하는 역할
// 목적 : 제이슨으로 받기, 토큰 만들기

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, CustomLoginHandler customLoginHandler) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.setAuthenticationFailureHandler(customLoginHandler);
    }

    // post의 /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.debug("디버그 : attemptAuthentication 요청됨");
        try {
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(),
                    loginReqDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e) {
            // AuthenticationException exception = (AuthenticationException) e;
            // e.printStackTrace();

            // try {
            // this.getFailureHandler().onAuthenticationFailure(request, response,
            // exception);
            // } catch (IOException | ServletException e1) {
            // e1.printStackTrace();
            // }

            // return null;

            // 이 오류를 리턴해야 failure 가 정상적으로 실행됨
            // 굳이 위처럼 넘겨주지 않아도, 리턴을 맞춰주면 결과값을 제대로 리턴하도록 돼있음

            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        log.debug("디버그 : SUCCESS AUTHENTICATION 요청됨");

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }

    @Override
    protected AuthenticationFailureHandler getFailureHandler() {

        log.debug("디버그 : 페일핸들러 실행됨");
        return super.getFailureHandler();
    }

}