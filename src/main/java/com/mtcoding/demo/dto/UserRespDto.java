package com.mtcoding.demo.dto;

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
}
