package com.example.demoproject.service;

import com.example.demoproject.domain.Member;
import com.example.demoproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean login(String username, String password) {
        Optional<Member> member = memberRepo.findById(username);
        if (member.isPresent()) {
            log.info("사용자 존재: username= {}", username);
            if (pwdEnc.matches(password, member.get().getPwd())) {
                log.info("비밀번호 일치");
                return true;
            } else {
                log.info("비밀번호 불일치");
            }
        } else {
            log.info("사용자 없음: username= {}", username);
        }
        return false;
    }

    @Override
    public Optional<Member> getMember(String username) {
        return memberRepo.findById(username);
    }

    @Override
    public boolean confirmUsername(String username) {
        return memberRepo.existsById(username);
    }

    @Override
    public void insertMember(Member vo) {
        vo.setPwd(pwdEnc.encode(vo.getPwd()));  // 비밀번호 암호화
        memberRepo.save(vo);
        log.info("새로운 회원 등록: username= {}", vo.getUsername());
    }

    @Override
    public Optional<Member> getMemberByNameAndEmail(String name, String email) {
        return memberRepo.findByUsernameAndEmail(name, email);
        // 만약 'name' 필드가 존재하지 않는다면, 아래와 같이 수정하세요:
        // return memberRepo.findByUsernameAndEmail(username, email);
    }

    @Override
    public Optional<Member> getMemberByUsernameAndPhone(String username, String phone) {
        return memberRepo.findByUsernameAndPhone(username, phone);
    }

    @Override
    @Transactional
    public void changePassword(String username, String newPassword) {
        String encodedPassword = pwdEnc.encode(newPassword);
        memberRepo.changePassword(username, encodedPassword);
        log.info("비밀번호 변경: username= {}", username);
    }
}