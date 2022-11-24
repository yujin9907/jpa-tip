package com.mtcoding.demo.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    // 네이티브 쿼리 쓸 필요없음
    // findByusername & 로 쓰는 방법
    @Query("select u from User u where username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    // 기본(update==save로) => 테스트 안 해도 됨
    // findbyid, findall, save(id 값 안넣으면 인서트, 넣어도 없으면 인서트, 넣고 있으면 업데이트), deletebyid
}
