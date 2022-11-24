package com.mtcoding.demo.config.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.mtcoding.demo.config.exception.CustomApiExceoption;;

public class CustomApiExceptionTest {

    @Test
    public void 커스텀익셉션테스트() {
        // given
        String msg = "아이디 없음";
        HttpStatus code = HttpStatus.BAD_REQUEST;

        // when
        CustomApiExceoption ex = new CustomApiExceoption(msg, code);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpSatusCode());
        assertThat(ex.getHttpSatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
