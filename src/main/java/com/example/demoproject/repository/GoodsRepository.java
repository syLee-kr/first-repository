package com.example.demoproject.repository;

import com.example.demoproject.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    // 신상품 조회
    @Query(value = "SELECT * FROM updateTime", nativeQuery = true)
    List<Goods> getNewProductList();

    // 상품 종류별 조회
    List<Goods> findByGoodsKindContaining(String goodsKind);

    // 전체 상품 조회(상품명으로 검색 포함)
    List<Goods> findByGoodsNameContainingOrderByGoodsName(String goodsName);

    // 전체 상품 조회(페이징 처리 포함)
    Page<Goods> findAllGoodsByGoodsNameContaining(String goodsName, Pageable pageable);
}