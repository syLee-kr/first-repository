package com.example.demoproject.service;

import com.example.demoproject.domain.Like;
import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import com.example.demoproject.repository.LikeRepository;
import com.example.demoproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private PostRepository postRepo;
    private LikeRepository likeRepo;

    // 생성자 주입
    @Autowired
    public LikeServiceImpl(PostRepository postRepo, LikeRepository likeRepo) {
        this.postRepo = postRepo;
        this.likeRepo = likeRepo;
    }

    @Transactional
    @Override
    public int toggleLike(int pseq, Member currentUser) {
        Post post = postRepo.findById(pseq)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        Optional<Like> existingLike = likeRepo.findByMemberAndPost(currentUser, post);

        if (existingLike.isPresent()) {
            // 이미 좋아요한 경우, 좋아요 취소
            likeRepo.delete(existingLike.get());
            post.setLikes(post.getLikes() - 1);
        } else {
            // 아직 좋아요하지 않은 경우, 좋아요 추가
            Like like = new Like();
            like.setMember(currentUser);
            like.setPost(post);
            likeRepo.save(like);
            post.setLikes(post.getLikes() + 1);
        }

        postRepo.save(post);
        return post.getLikes();
    }
}

