package com.mtcoding.demo.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    // 네이티브 쿼리 쓸 필요없음
    // findByusername & 로 쓰는 방법
    @Query("select u form User u where username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
