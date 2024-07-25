package com.himedia.srshopserver.controller;

import com.himedia.srshopserver.dto.Order_v;
import com.himedia.srshopserver.entity.Cart;
import com.himedia.srshopserver.entity.Cart_view;
import com.himedia.srshopserver.entity.Member;
import com.himedia.srshopserver.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cs;

    @PostMapping("/insertCart")
    public HashMap<String, Object> insertCart(@RequestBody Cart cart) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        cs.insertCart( cart );
        return result;
    }

    @GetMapping("/getcartlist")
    public HashMap<String, Object> getcartlist( HttpServletRequest request ) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        Member loginUser = (Member)session.getAttribute("loginUser");
        List<Cart_view> list = cs.getCartList( loginUser.getUserid() );
        result.put("cartList", list);
        int totalPrice = 0;
        for( Cart_view c : list) {
            totalPrice += ( c.getPrice2() * c.getQuantity() );
        }
        result.put("totalPrice", totalPrice);
        return result;
    }

    @DeleteMapping("/deletecart/{cseq}")
    public HashMap<String, Object> deleteCart(@PathVariable("cseq") int cseq) {
        HashMap<String, Object> result = new HashMap<>();
        cs.deletecart( cseq );
        return result;
    }
}
