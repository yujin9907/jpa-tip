package com.mtcoding.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// http code 성공
// 200 : select, delete, update (get, delete, put)
// 201 : insert 포스트 요청만 201, 로그인 제외

// 변경될 일 없으므로 세터 필요 없음. => 이런 경우 final을 습관화 -> final이면 all =>require
@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {
    private final String msg;
    private final T data;
}
