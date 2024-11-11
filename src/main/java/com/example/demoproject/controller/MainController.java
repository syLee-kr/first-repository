package com.example.demoproject.controller;

import com.example.demoproject.PostDto;
import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import com.example.demoproject.repository.LikeRepository;
import com.example.demoproject.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final LikeRepository likeRepository;
    private PostService ps;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(PostService ps, LikeRepository likeRepository) {
        this.ps = ps;
        this.likeRepository = likeRepository;
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("user");
        if (member == null) {
            logger.warn("Unauthorized access attempt to /main");
            return "redirect:/login";
        }

        List<Post> posts = ps.getPosts();
        List<PostDto> postDtos = posts.stream()
                .map(post -> {
                    boolean likedByCurrentUser = likeRepository.existsByMemberAndPost(member, post);
                    PostDto postDto = new PostDto(post, likedByCurrentUser);
                    logger.debug("Image URL: " + postDto.getImageUrl());
                    return postDto;
                })
                .collect(Collectors.toList());

        model.addAttribute("posts", postDtos);
        return "main";
    }
}
