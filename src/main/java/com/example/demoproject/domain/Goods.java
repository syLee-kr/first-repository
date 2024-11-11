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
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    gseq;               //  상품 시퀀스
    private String goodsName;      //  상품 이름
    private String goodsDesc;      //  상품 설명
    private int    price;           //  가격
    private int    sale;            //  할인률
    private int    quantity;        //  수량
    private String goodsKind;            //  종류

    @Column(columnDefinition = "varchar2(255) default 'images/default2.png'")
    private String goods_image;     //  상품 이미지

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updateTime = new Date();  //  상품 등록일
}
