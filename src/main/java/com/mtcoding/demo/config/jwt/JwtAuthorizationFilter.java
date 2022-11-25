package com.mtcoding.demo.config.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mtcoding.demo.config.auth.LoginUser;
import com.mtcoding.demo.config.enums.UserEnum;
import com.mtcoding.demo.domain.user.User;
import com.mtcoding.demo.domain.user.UserRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

// jsesseionid가 먹지 않음 : stateless하게 구현했으므로
// => 목적 : 수정된 로직에 따른 인가 로직 커스텀(인가 필터)

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager); // 부모한테 넘기게 돼 있어서 여기서 필요없는데 적어줌
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("디버그 : ZATION 요청됨");

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            // chain.doFilter(request, response); 필터를 타는 게 아니라 토큰이 없다는 응답을 해야 됨
            return;
        }
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        try {

            // String username =
            // JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
            // .getClaim("username").asString();

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
            Long id = decodedJWT.getClaim("id").asLong();
            String role = decodedJWT.getClaim("role").asString();
            User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
            LoginUser loginUser = new LoginUser(user);

            // Optional<User> userOP = userRepository.findByUsername(username);

            // if (userOP.isPresent()) {
            // User userPS = userOP.get();
            // UsernamePasswordAuthenticationToken authenticationToken = new
            // UsernamePasswordAuthenticationToken(
            // userPS.getUsername(),
            // userPS.getPassword());

            // authenticationManager.authenticate(authenticationToken);

            // 수정된 코드

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                    loginUser.getPassword(), loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
            return;
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}