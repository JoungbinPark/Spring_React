package com.himedia.srshopserver.controller;

import com.himedia.srshopserver.entity.Product;
import com.himedia.srshopserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService ps;

    @GetMapping("/bestPro")
    public HashMap<String, Object> bestProduct(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("bestProduct", ps.getBestProduct());
        return result;
    }

    @GetMapping("/newPro")
    public HashMap<String, Object> newProduct(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("newProduct", ps.getNewProduct());
        return result;
    }

    @GetMapping("/kindlist/{kind}")
    public List<Product> kindlist(@PathVariable("kind") String kind){
        HashMap<String, Object> result = new HashMap<>();
        List<Product> list = ps.getListKind(kind);
        return list;
    }
}
