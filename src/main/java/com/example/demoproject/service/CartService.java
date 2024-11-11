package com.example.demoproject.service;

import com.example.demoproject.domain.Cart;
import com.example.demoproject.domain.Goods;
import com.example.demoproject.domain.Member;

import java.util.List;

public interface CartService {

    //  장바구니 저장
    void insertCart(Cart vo);

    //  장바구니 항목 불러오기
    List<Cart> getCartList(Member vo);

    //  장바구니 삭제
    void deleteCart(Member vo, Goods goods);

    //  장바구니 전체 비우기
    void deleteAllCarts(Member vo);

    //  장바구니
    void updateCart(Member vo, Goods goods, int quantity);

    int totalPrice(Member vo);
}
