package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.ProductDao;
import com.himedia.srshopserver.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    ProductDao pdao;

    public List<Product> getBestProduct() {
        return pdao.getBestProduct();
    }

    public List<Product> getNewProduct() {
        return pdao.getNewProduct();
    }

    public List<Product> getListKind(String kind) {
        return pdao.getKindList( kind );
    }

    public Product getProduct(int pseq) {
        return pdao.getProduct( pseq );
    }
}
