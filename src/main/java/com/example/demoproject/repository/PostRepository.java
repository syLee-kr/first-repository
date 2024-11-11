package com.example.demoproject.repository;

import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // 특정 멤버의 게시물을 모두 가져오는 메서드
    List<Post> findByMember(Member member);

    // 제목으로 게시물을 검색하는 메서드
    List<Post> findByTitleContaining(String title);

    // 최신 피드를 페이징하여 날짜 순으로 정렬하여 가져오는 메서드
    Page<Post> findAllByOrderByPostdateDesc(Pageable pageable);
}