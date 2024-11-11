package com.example.demoproject.service;

import com.example.demoproject.domain.Member;
import com.example.demoproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder pwdEnc;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepo, PasswordEncoder pwdEnc) {
        this.memberRepo = memberRepo;
        this.pwdEnc = pwdEnc;
    }

    @Override
    public boolean loginId(Member vo) {
        Optional<Member> member = memberRepo.findById(vo.getId());
        if (member.isPresent()) {
            System.out.println("사용자 존재: ID=" + vo.getId());
            log.info("사용자 존재: ID= {}", vo.getId());
            if (pwdEnc.matches(vo.getPwd(), member.get().getPwd())) {
                System.out.println("비밀번호 일치");
                return true;
            } else {
                System.out.println("비밀번호 불일치");
            }
        } else {
            System.out.println("사용자 없음: ID=" + vo.getId());
        }
        return false;
    }

    @Override
    public Optional<Member> getMember(String id) {
        return memberRepo.findById(id);
    }

    @Override
    public boolean confirmId(String id) {
        return memberRepo.existsById(id);
    }

    @Override
    public void insertMember(Member vo) {
        vo.setPwd(pwdEnc.encode(vo.getPwd()));  // 비밀번호 암호화
        memberRepo.save(vo);
    }

    @Override
    public Optional<Member> getIdByNameAndEmail(String name, String email) {
        return memberRepo.findByNameAndEmail(name, email);
    }

    @Override
    public Optional<Member> getPwdByIdAndPhone(String id, String phone) {
        return memberRepo.findByIdAndPhone(id, phone);
    }

    @Override
    public void changePassword(Member vo) {
        String encodedPassword = pwdEnc.encode(vo.getPwd());
        memberRepo.changePassword(vo.getId(), encodedPassword);
    }
}
