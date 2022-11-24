package com.mtcoding.demo.config.exception;

import lombok.Getter;

// 빈생성자 절대 만들지 않음. 내가 new 하니까
@Getter
public class CustomApiExceoption extends RuntimeException {

    private final int httpSatusCode;

    public CustomApiExceoption(String msg, int httpSatusCode) {
        super(msg);
        this.httpSatusCode = httpSatusCode;
    }

}
