package com.mtcoding.demo.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mtcoding.demo.domain.AudingTime;
import com.mtcoding.demo.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "account")
@Entity
public class Account extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private Long number; // 계좌 번호

    @Column(nullable = false, length = 20)
    private String ownerName; // 계좌주 실명

    @Column(nullable = false, length = 4)
    private String password; // 계좌 비밀번호

    @Column(nullable = false)
    private Long balance; // 잔액

    @Column(nullable = false)
    private Boolean isActive; // 계좌 활성화 여부

    // 커멜케이스는 db에 언더스코어로 생성됨 ( 디폴트 ) ; 아래 설정으로 조절 가능
    // # '[hibernate.default_batch_fetch_size]': 100 추후 사용

    @ManyToOne(fetch = FetchType.LAZY) // 내필드 투 받는필드
    private User user;

}
