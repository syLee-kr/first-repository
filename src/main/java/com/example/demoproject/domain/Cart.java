package com.example.demoproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 장바구니 항목 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;  // 장바구니에 담긴 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 장바구니 항목 소유 회원

    private int quantity;  // 상품 수량

    // 편의 메소드: 개별 총 가격을 계산
    public int getTotalPrice() {
        return goods.getPrice() * quantity;
    }
}