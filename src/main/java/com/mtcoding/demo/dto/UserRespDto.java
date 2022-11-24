package com.mtcoding.demo.dto;

import java.time.format.DateTimeFormatter;

import com.mtcoding.demo.domain.user.User;

import lombok.Getter;
import lombok.Setter;

public class UserRespDto {
    @Getter
    @Setter
    public static class joinRepsDto {
        private long id;
        private String username;
        private String password;

        public joinRepsDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword(); // 확인을 위해서 달아둠, 실제에선 제외
        }
    }

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy--MM-dd HH:mm:ss"));
            // 나중에 유틸 만들기 customdateutil 로 만듦
        }

    }
}
