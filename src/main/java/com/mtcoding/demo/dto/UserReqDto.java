package com.mtcoding.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mtcoding.demo.config.enums.UserEnum;
import com.mtcoding.demo.domain.user.User;

import lombok.Getter;
import lombok.Setter;

public class UserReqDto {

    // 인증에 관련된 건 도메인 기능 리퀘스트 디티오 아니고
    // 이런식으로 작성
    @Getter
    @Setter
    public static class JoinReqDto {

        // notnull과 blank 차이 : blank=null과 빈값(notempty)을 체크함

        // https://hyeran-story.tistory.com/81 정리하기
        @Size(min = 2, max = 20) // 문자열 검증만 가능 @Digits : 숫자 검증 참고
        @NotBlank(message = "유저네임은 필수입니다")
        private String username;

        private String password;
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .role(UserEnum.CUSTOMER) // 롤은 직접 부여해야 됨.
                    .build();
        }
    }
}
