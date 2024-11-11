package com.example.demoproject.repository;

import com.example.demoproject.domain.Cart;
import com.example.demoproject.domain.Goods;
import com.example.demoproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 특정 회원의 장바구니 항목 조회
    List<Cart> findByMember(Member member);

    // 특정 회원의 장바구니에서 특정 상품 조회 (중복 방지, 검증용)
    Optional<Cart> findByMemberAndGoods(Member member, Goods goods);

    // 특정 회원의 장바구니 전체 비우기
    void deleteByMember(Member member);

    // 특정 상품이 장바구니에 담긴 총 수량
    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.goods = :goods")
    Integer findTotalQuantityByGoods(@Param("goods") Goods goods);

    // 특정 회원의 장바구니 총 금액 계산
    @Query("SELECT SUM(c.goods.price * c.quantity) FROM Cart c WHERE c.member = :member")
    Integer findTotalPriceByMember(@Param("member") Member member);

    // 특정 회원의 장바구니에서 특정 상품 수량 업데이트
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.member = :member AND c.goods = :goods")
    void updateQuantityByMemberAndGoods(@Param("member") Member member, @Param("goods") Goods goods, @Param("quantity") int quantity);
}