package com.example.demoproject;

import com.example.demoproject.domain.Member;
import com.example.demoproject.repository.MemberRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class MemberRepoTest {

    @Autowired
    MemberRepository memberRepo;

    @Disabled
    @Test
    public void memberInsert() {
        Member vo = Member.builder()
                .id("tjdduq410")
                .name("이성엽")
                .pwd("1234")
                .email("tjdduq410@naver.com")
                .phone("010-2886-3097")
                .address("서울시 후암동")
                .regdate(new Date())
                .build();
        memberRepo.save(vo);
    }

    @Disabled
    @Test
    public void getMember(){
        Optional<Member> data = memberRepo.findById("tjdduq410");
        if (data.isEmpty()) {
            System.out.println("아이디가 존재하지 않습니다.");
        } else {
            Member member = data.get();
            System.out.println(member);
        }
    }

}
