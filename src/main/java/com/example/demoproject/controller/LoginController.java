package com.example.demoproject.controller;

import com.example.demoproject.domain.Member;
import com.example.demoproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final MemberService ms;

    @Autowired
    public LoginController(MemberService ms) {
        this.ms = ms;
    }

    @GetMapping("/login")
    public String loginView(HttpSession session) {
        // 이미 로그인된 상태라면 메인 페이지로 리디렉션
        if (session.getAttribute("user") != null) {
            return "redirect:/main";
        }
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginSubmit(Member vo, HttpSession session, Model model) {
        System.out.println("로그인 시도: " + vo.getId());
        boolean isLoginSuccess = ms.loginId(vo);

        if (isLoginSuccess) {
            // 로그인 성공 시 세션에 사용자 정보를 저장
            session.setAttribute("user", vo);

            // CSRF 토큰 재생성
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(vo.getId(), vo.getPwd())
            );

            return "redirect:/main";  // 로그인 성공 시 메인 페이지로 리디렉션
        } else {
            model.addAttribute("loginFail", true);  // 로그인 실패 여부 추가
            return "login/loginForm";  // 실패 시 뷰 이름 반환하여 모델 속성 유지
        }
    }

    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("user", new Member());
        return "login/join";
    }

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@RequestParam String userId) {
        boolean exists = ms.confirmId(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/joinOk")
    public String joinOk(Member user, HttpSession session, Model model) {
        System.out.println("회원가입 요청: " + user);
        ms.insertMember(user);
        System.out.println("회원가입 성공");
        session.setAttribute("member", user);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화하여 모든 속성 제거
        return "redirect:/login";
    }
}
