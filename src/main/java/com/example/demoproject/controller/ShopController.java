package com.example.demoproject.controller;

import com.example.demoproject.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private GoodsRepository gr;

    @Autowired
    public ShopController(GoodsRepository gr) {
        this.gr = gr;
    }

    @GetMapping
    public String shop(Model model) {
        model.addAttribute("goodsList", gr.findAll());
        return "goodsShop/shop"; // 프래그먼트가 아닌 전체 템플릿 반환
    }
}
