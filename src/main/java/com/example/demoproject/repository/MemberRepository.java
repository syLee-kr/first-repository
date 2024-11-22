package com.example.demoproject.repository;

import com.example.demoproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    // username과 email을 바탕으로 회원을 조회
    Optional<Member> findByUsernameAndEmail(String username, String email);

    // username과 전화번호를 바탕으로 회원을 조회
    Optional<Member> findByUsernameAndPhone(String username, String phone);

    // 비밀번호 변경 (JPQL 사용)
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.pwd = :pwd WHERE m.username = :username")
    void changePassword(@Param("username") String username, @Param("pwd") String pwd);
}
