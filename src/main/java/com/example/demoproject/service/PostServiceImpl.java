package com.example.demoproject.service;

import com.example.demoproject.domain.Like;
import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import com.example.demoproject.repository.LikeRepository;
import com.example.demoproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository pr;
    private final LikeRepository li;

    @Autowired
    public PostServiceImpl(PostRepository pr, LikeRepository li) {
        this.pr = pr;
        this.li = li;
    }

    @Override
    public Post savePost(Post post) {
        pr.save(post);
        return post;
    }

    @Override
    public void deletePost(Post post) {
        pr.delete(post);
    }

    @Override
    public List<Post> getPosts() {
        return pr.findAll();
    }

    @Override
    public List<Post> getPostsByMember(Member member) {
        return pr.findByMember(member);
    }

    @Override
    public List<Post> searchPostsByTitle(String title) {
        return pr.findByTitleContaining(title);
    }

    @Override
    public Page<Post> getLatestPosts(Pageable pageable) {
        return pr.findAllByOrderByPostdateDesc(pageable);
    }

    @Transactional
    public int toggleLike(int pseq, Member currentUser) {
        Post post = pr.findById(pseq)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        Optional<Like> existingLike = li.findByMemberAndPost(currentUser, post);

        if (existingLike.isPresent()) {
            // 이미 좋아요한 경우, 좋아요 취소
            li.delete(existingLike.get());
            post.setLikes(post.getLikes() - 1);
        } else {
            // 아직 좋아요하지 않은 경우, 좋아요 추가
            Like like = Like.builder()
                    .member(currentUser)
                    .post(post)
                    .build();
            li.save(like);
            post.setLikes(post.getLikes() + 1);
        }

        pr.save(post);
        return post.getLikes();
    }
}