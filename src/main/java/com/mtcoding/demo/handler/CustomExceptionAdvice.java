package com.mtcoding.demo.handler;

import javax.swing.SpinnerDateModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mtcoding.demo.config.exception.CustomApiExceoption;
import com.mtcoding.demo.dto.ResponseDto;

@RestControllerAdvice
public class CustomExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass()); // 임포트 org.sl4 주의

    @ExceptionHandler(CustomApiExceoption.class) // 이거 클래스 이름과 겹치면 ㅈ같이 임포트함
    public ResponseEntity<?> apiException(CustomApiExceoption e) {

        log.debug("디버그 : custom exception advice 실행됨");

        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), null), e.getHttpSatusCode());
        // 바디에 상태코드는 프론트엔드 보라고 / status 속성은 브라우저를 위함
        // 상태코드를 통해 그냥 줘도 됨. responsedto에서 빼도 됨.
        // 일단 커스터마이징할 수 있으니까 남겨둠
    }
}
