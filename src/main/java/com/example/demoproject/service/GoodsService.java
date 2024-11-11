package com.example.demoproject.service;

import com.example.demoproject.domain.Goods;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodsService {

    //  최신 상품 조회
    public List<Goods> getNewGoodsList();

    //  종류별 상품 조회
    public List<Goods> getGoodsListByKind(String goodsKind);

    //  이름별 상품 조회
    public List<Goods> getAllGoods(String goodsName);

    //  제품 등록
    public void insertGoods(Goods vo);

    //  제품 업데이트
    public void updateGoods(Goods vo);

    //  제품 삭제
    public void deleteGoods(Goods vo);

    //  모든 항목
    public Page<Goods> getAllGoodsByName(String goodsName, int page, int size);
}
