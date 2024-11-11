package com.example.demoproject.domain;

import com.example.demoproject.PostDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pseq;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private Member member;

    @Column(columnDefinition = "varchar2(255) default 'images/nullimage.png'")
    private String imagePath;   //  게시물 이미지

    private String title;       //  제목
    private String content;     //  본문
    private int heart;          //  하트
    private int likes;          //  좋아요
    private String comments;    //  댓글

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date postdate;


    @PrePersist
    protected void onCreate() {
        this.postdate = new Date();
    }
}
