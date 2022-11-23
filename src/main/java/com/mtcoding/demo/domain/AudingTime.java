package com.mtcoding.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // 이 클래스 상속하면, 테이블의 컬럼으로 만들라
@EntityListeners(AuditingEntityListener.class) // 인서트 업데이트 감시 리스너
public abstract class AudingTime { // 이 자체만을 new 할 수 없도록 추상 클래스화

    // 시간(기본 필드) 익스탠즈로 사용하도록

    @LastModifiedDate // insert, update 시에 현재 시간
    @Column(nullable = false)
    protected LocalDateTime updatedAt;
    @CreatedDate // insert 시 현재 시간
    @Column(nullable = false)
    protected LocalDateTime createdAt;

}
