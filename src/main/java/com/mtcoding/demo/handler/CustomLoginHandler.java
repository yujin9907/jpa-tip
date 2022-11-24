package com.mtcoding.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtcoding.demo.config.auth.LoginUser;
import com.mtcoding.demo.config.exception.CustomApiExceoption;
import com.mtcoding.demo.dto.ResponseDto;
import com.mtcoding.demo.dto.UserRespDto.LoginRespDto;

@Component // new를 두번할 필요없이 ioc 컨테이너에서 싱글톤으로 관리 success failure 두번 사용하니까
public class CustomLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final Logger log = LoggerFactory.getLogger(getClass()); // 임포트 org.sl4 주의

    // 이 om 부터 출력되는 부분 프라이빗 메서드화 해서 재사용하기

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.debug("디버그 : custom login handler 실행 success");

        // 세션 저장 확인
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ObjectMapper om = new ObjectMapper();
        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        ResponseDto<?> responseDto = new ResponseDto<>("로그인 성공", loginRespDto);
        String responseBody = om.writeValueAsString(responseDto);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        response.getWriter().println(responseBody);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.debug("디버그 : custom login handler 실행됨");
        ObjectMapper om = new ObjectMapper();
        ResponseDto<?> responseDto = new ResponseDto<>("로그인 실패", null);
        String responseBody = om.writeValueAsString(responseDto);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(400);
        response.getWriter().println(responseBody); // 프린트라이트는 플러시해줄 필요가 없음 println이 내장돼 있기 때문에

        // 필터쪽에서 터졌으므로 커스텀익셉션어드바이스가 낚아챌수 ㅇ벗음(아직 컨트룰까지 오지 않았으므로)

    }

}
