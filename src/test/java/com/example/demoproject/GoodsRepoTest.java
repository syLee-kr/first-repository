package com.example.demoproject;

import com.example.demoproject.domain.Goods;
import com.example.demoproject.repository.GoodsRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GoodsRepoTest {

    @Autowired
    private GoodsRepository goodsRepo;

    @Disabled
    @Test
    public void goodsInsert(){
        Goods goods = Goods.builder()
                .goodsName("만화책")
                .goodsKind("책")
                .goodsDesc("내가 만든 책")
                .quantity(3)
                .price(5000)
                .sale(10)
                .build();

        Goods goods2 = Goods.builder()
                .goodsName("만화")
                .goodsKind("책")
                .goodsDesc("만든 책")
                .quantity(4)
                .price(7000)
                .sale(20)
                .build();

        goodsRepo.save(goods);
        goodsRepo.save(goods2);
    }

    @Disabled
    @Test
    public void getGoods() {
        List<Goods> goods = goodsRepo.findByGoodsNameContainingOrderByGoodsName("만화");

        System.out.println("상품 = " + goods);
    }
    @Disabled
    @Test
    public void getGoods2() {
        List<Goods> book = goodsRepo.findByGoodsKindContaining("책");

        System.out.println("book = " + book);
    }
}
