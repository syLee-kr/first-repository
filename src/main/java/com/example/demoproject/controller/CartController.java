package com.example.demoproject.controller;

import com.example.demoproject.domain.Cart;
import com.example.demoproject.domain.Member;
import com.example.demoproject.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private CartService cs;

    @Autowired
    public CartController(CartService cs) {}

    /*@GetMapping("/cart")
    public String viewCart(Member vo, Model model) {
        List<Cart> cartItems = cs.getCartList(vo);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cs.totalPrice(vo));
        return "main";
    }*/
}
