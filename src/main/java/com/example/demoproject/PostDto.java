package com.example.demoproject;

import com.example.demoproject.domain.Post;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private int pseq;
    private String username;
    private String userImage;
    private String imageUrl;
    private String content;
    private int likes;
    private String comments;
    private Date postdate;
    private boolean likedByCurrentUser;

    public PostDto(Post post) {
        this.pseq = post.getPseq();
        this.username = post.getMember().getId();
        this.userImage = post.getMember().getProfileImage();
        this.imageUrl = post.getImagePath();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.postdate = post.getPostdate();
    }
    public PostDto(Post post, boolean likedByCurrentUser) {
        this(post); // 첫 번째 생성자 호출하여 필드 초기화
        this.likedByCurrentUser = likedByCurrentUser;
    }
}