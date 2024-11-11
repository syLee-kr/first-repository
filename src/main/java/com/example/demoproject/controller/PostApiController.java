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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;
    private final LikeRepository likeRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostApiController.class);

    @Autowired
    public PostApiController(PostService postService, LikeRepository likeRepository) {
        this.postService = postService;
        this.likeRepository = likeRepository;
    }

    @GetMapping
    public String getPosts(@RequestParam("page") int page, HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("user");
        if (member == null) {
            logger.warn("Unauthorized access attempt to /getPosts");
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by("postdate").descending());
        Page<Post> postsPage = postService.getLatestPosts(pageable);

        List<PostDto> postDtos = postsPage.stream()
                .map(post -> {
                    boolean likedByCurrentUser = likeRepository.existsByMemberAndPost(member, post);
                    return new PostDto(post, likedByCurrentUser);
                })
                .collect(Collectors.toList());

        model.addAttribute("posts", postDtos);
        return "main-container :: main-container";  // HTML 조각을 반환
    }

    // 새로운 게시글을 저장하는 메서드
    @PostMapping("/create")
    public PostDto createPost(@RequestBody Post post) {
        Post savedPost = postService.savePost(post);
        return new PostDto(savedPost);  // 저장된 게시물을 PostDto로 반환
    }

    @PostMapping("/{pseq}/like")
    public ResponseEntity<Integer> toggleLike(@PathVariable int pseq, HttpSession session) {
        Member currentUser = (Member) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        int likeCount = postService.toggleLike(pseq, currentUser);
        return ResponseEntity.ok(likeCount);
    }
}