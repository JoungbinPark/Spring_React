package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.CartDao;
import com.himedia.srshopserver.entity.Cart;
import com.himedia.srshopserver.entity.Cart_view;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    CartDao cdao;

    public void insertCart(Cart cart) {
        cdao.insertCart( cart );
    }

    public List<Cart_view> getCartList(String userid) {
        return cdao.getCartList( userid );
    }

    public void deletecart(int cseq) {
        cdao.deleteCart( cseq );
    }
}
