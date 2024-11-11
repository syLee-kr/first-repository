package com.example.demoproject.service;

import com.example.demoproject.domain.Goods;
import com.example.demoproject.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private GoodsRepository gr;

    @Autowired
    public GoodsServiceImpl(GoodsRepository gr) {
        this.gr = gr;
    }

    @Override
    public List<Goods> getNewGoodsList() {
        return gr.getNewProductList();
    }

    @Override
    public List<Goods> getGoodsListByKind(String goods_kind) {
        return gr.findByGoodsKindContaining(goods_kind);
    }

    @Override
    public List<Goods> getAllGoods(String goods_name) {
        return gr.findByGoodsNameContainingOrderByGoodsName(goods_name);
    }

    @Override
    public void insertGoods(Goods vo) {
        gr.save(vo);
    }

    @Override
    public void updateGoods(Goods vo) {
        Goods g = gr.findById(vo.getGseq()).orElseThrow(() -> new RuntimeException("Goods not found"));
        vo.setUpdateTime(g.getUpdateTime());  // 기존의 등록일 유지
        gr.save(vo);
    }

    @Override
    public void deleteGoods(Goods vo) {
        gr.delete(vo);
    }

    @Override
    public Page<Goods> getAllGoodsByName(String goods_name, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size, Sort.by("goods_name").ascending());
        return gr.findAllGoodsByGoodsNameContaining(goods_name, paging);
    }
}
