package com.example.demoproject.domain;

import jakarta.persistence.*;
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
public class Member {

    @Id
    private String id;                  //  아이디
    private String pwd;                 //  비밀 번호
    private String birthday;            //  생일
    private String name;                //  이름
    private String email;               //  이메일
    private String phone;               //  전화 번호
    private String address;             //  주소
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date regdate = new Date();  //  가입일

    @Column(columnDefinition = "varchar2(255) default 'images/default.png'")
    private String profileImage;   //  프로필 이미지
}
