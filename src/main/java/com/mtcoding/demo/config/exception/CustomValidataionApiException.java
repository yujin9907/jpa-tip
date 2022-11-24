package com.mtcoding.demo.config.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomValidataionApiException extends RuntimeException {

    // 밸리데이션 익셉션은 무조건 400대

    private final HttpStatus httpSatusCode;
    private Map<String, String> errorMap;

    public CustomValidataionApiException(String msg) {
        super(msg); // 유효성 검사 실패로 고정 (안 받아도 됨)
        this.errorMap = errorMap; // 구체적으로 어떤 요청이 잘못 됐는지
        this.httpSatusCode = HttpStatus.BAD_REQUEST;
    }
}
