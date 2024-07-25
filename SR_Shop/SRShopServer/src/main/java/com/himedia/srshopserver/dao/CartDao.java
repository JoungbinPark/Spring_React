package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.entity.Cart;
import com.himedia.srshopserver.entity.Cart_view;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    @Autowired
    EntityManager em;

    public void insertCart(Cart cart) {
        em.persist(cart);
    }

    public List<Cart_view> getCartList(String userid) {
        String sql = "select c from Cart_view c where c.userid=:userid";
        Query query = em.createQuery(sql);
        query.setParameter("userid", userid);
        return query.getResultList();
    }


    public void deleteCart(int cseq) {
        Cart cart = em.find(Cart.class, cseq);
        em.remove(cart);
    }
}
