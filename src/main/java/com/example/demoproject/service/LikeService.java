package com.example.demoproject.service;

import com.example.demoproject.domain.Member;

public interface LikeService {

    public int toggleLike(int pseq, Member currentUser);
}
