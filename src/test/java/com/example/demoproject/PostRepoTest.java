package com.example.demoproject;

import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import com.example.demoproject.repository.MemberRepository;
import com.example.demoproject.repository.PostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostRepoTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Disabled
    @Test
    public void postInsert() {
        Optional<Member> memid = memberRepository.findById("tjdduq410");
        Post post = Post.builder()
                .member(memid.get())
                .title("두번째 글")
                .content("ㅎㅇ~")
                .heart(4)
                .likes(4)
                .build();

        postRepository.save(post);
    }

    @Disabled
    @Test
    public void allPost() {
        List<Post> posts = postRepository.findAll();

        System.out.println(posts);
    }

    @Disabled
    @Test
    public void findPostById() {
        List<Post> post = postRepository.findByTitleContaining("두번째 글");
        System.out.println(post);
    }
}
