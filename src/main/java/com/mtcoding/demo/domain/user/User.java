package com.mtcoding.demo.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mtcoding.demo.config.enums.UserEnum;
import com.mtcoding.demo.domain.AudingTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 하이버네이트를 위해 (하이버네이트만 사용가능하도록 설정)
// 리플렉션은 접근 제어자와 관련없이 무조건 다 분석할 수 있음(따라서 하이버네이트 접근 가능)
@Getter
@Entity
@Table(name = "users") // 꼭 붙여주기로 함, jpa로 생성할 때 디폴트로 첫글자 대문자 됨 주의. 그래서 table로 이름 지정 확실하게
public class User extends AudingTime { // 스프링 : 테이블을 new 생성하고, getter로 불러와서 noarg 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // length가 필수인 이유? 하드디스크상 rdb에 table의 기본 용량-table space : 적절히 만드는 것이 효율적
    @Column(unique = true, nullable = false, length = 20) // 디폴트 255자
    private String username;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // enum 사용시 어노테이션
    private UserEnum role; // ADMIN, CUSTOMER
}
