package com.example.demoproject.controller;

import com.example.demoproject.PostDto;
import com.example.demoproject.domain.Post;
import com.example.demoproject.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 폼을 보여주는 메서드
    @GetMapping("/create")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());  // 빈 Post 객체를 모델에 추가
        return "createPostForm";  // templates/createPostForm.html 파일을 반환
    }
}