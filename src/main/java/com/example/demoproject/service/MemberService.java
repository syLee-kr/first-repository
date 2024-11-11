package com.example.demoproject.service;

import com.example.demoproject.domain.Member;

import java.util.Optional;

public interface MemberService {

    // 로그인 여부 확인
    boolean loginId(Member vo);

    // 주어진 아이디로 멤버 정보 조회
    Optional<Member> getMember(String id);

    // 회원 가입 시 아이디 중복 체크
    boolean confirmId(String id);

    // 새로운 회원 등록
    void insertMember(Member vo);

    // 이름과 이메일로 회원 정보 가져오기
    Optional<Member> getIdByNameAndEmail(String name, String email);

    // 아이디와 전화번호로 회원 정보 가져오기
    Optional<Member> getPwdByIdAndPhone(String id, String phone);

    // 비밀번호 변경
    void changePassword(Member vo);
}