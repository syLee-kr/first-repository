package com.example.demoproject.service;

import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    // 게시물 저장
    Post savePost(Post post);

    // 게시물 삭제
    void deletePost(Post post);

    // 모든 게시물 가져오기
    List<Post> getPosts();

    // 특정 멤버의 게시물 가져오기
    List<Post> getPostsByMember(Member member);

    // 제목을 포함하는 게시물 검색
    List<Post> searchPostsByTitle(String title);

    // 최신 게시물 페이징하여 가져오기
    Page<Post> getLatestPosts(Pageable pageable);

    int toggleLike(int pseq, Member currentUser);
}