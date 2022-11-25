package com.mtcoding.demo.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtcoding.demo.dto.ResponseDto;
import com.mtcoding.demo.dto.UserReqDto.JoinReqDto;
import com.mtcoding.demo.dto.UserRespDto.joinRepsDto;
import com.mtcoding.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api") // 무조건 붙이기
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> joinControll(@RequestBody JoinReqDto joinReqDto) {
        joinRepsDto join = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>("회원가입 성공", join), HttpStatus.CREATED);
    }

    @GetMapping("/api/transaction")
    public ResponseEntity<?> testauthenroization() {
        return new ResponseEntity<>(new ResponseDto<>("정상", null), HttpStatus.CREATED);
    }

}
