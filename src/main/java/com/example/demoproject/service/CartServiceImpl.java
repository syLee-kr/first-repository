package com.example.demoproject.service;

import com.example.demoproject.domain.Cart;
import com.example.demoproject.domain.Goods;
import com.example.demoproject.domain.Member;
import com.example.demoproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cr;

    @Autowired
    public CartServiceImpl(CartRepository cartRepo) {
        this.cr = cartRepo;
    }

    @Override
    public void insertCart(Cart vo) {
        cr.save(vo);
    }

    @Override
    public List<Cart> getCartList(Member vo) {
        return Collections.unmodifiableList(cr.findByMember(vo));
    }

    @Override
    public void deleteCart(Member vo, Goods goods) {
        Optional<Cart> byMemberAndGoods = cr.findByMemberAndGoods(vo, goods);
        byMemberAndGoods.ifPresent(cr::delete);  // 특정 항목이 존재할 때만 삭제
    }

    @Override
    @Transactional
    public void deleteAllCarts(Member vo) {
        cr.deleteByMember(vo);
    }

    @Override
    @Transactional
    public void updateCart(Member vo, Goods goods, int quantity) {
        cr.updateQuantityByMemberAndGoods(vo, goods, quantity);
    }

    @Override
    public int totalPrice(Member vo) {
        return cr.findTotalPriceByMember(vo);
    }
}