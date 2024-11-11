package com.example.demoproject.repository;

import com.example.demoproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    //  아이디와 이메일을 바탕으로 회원을 조회
    Optional <Member> findByNameAndEmail(String name, String email);

    //  아이디와 전화번호를 바탕으로 회원을 조회
    Optional<Member> findByIdAndPhone(String id, String phone);

    // 비밀번호 변경 (JPQL 사용)
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.pwd = :pwd WHERE m.id = :id")
    void changePassword(@Param("id") String id, @Param("pwd") String pwd);

}
