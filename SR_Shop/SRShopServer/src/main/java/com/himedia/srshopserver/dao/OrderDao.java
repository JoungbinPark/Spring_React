package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.entity.Cart;
import com.himedia.srshopserver.entity.Order_detail;
import com.himedia.srshopserver.entity.Order_view;
import com.himedia.srshopserver.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    EntityManager em;

    public void insertOrders(Orders orders) {
        em.persist(orders);
    }

    public int lookupMaxOseq() {
        String sql = "select max(o.oseq) from Orders o";
        int result = (Integer) em.createQuery(sql).getSingleResult();
        return result;
    }

    public Cart getCart(int cseq) {
        return em.find(Cart.class, cseq);
    }

    public void insertOrderDetail(Order_detail order_detail) {
        em.persist(order_detail);
    }

    public void deleteCart(Cart cart) {
        em.remove( cart );
    }

    public List<Order_view> getOrderByOseq(int oseq) {
        String sql = "select o from Order_view o where o.oseq=:oseq " + " order by o.odseq desc";
        List<Order_view> list = em.createQuery(sql, Order_view.class)
                .setParameter("oseq", oseq)
                .getResultList();
        return list;
    }

    public List<Integer> getOseqListIng(String userid) {
        String sql = "select distinct o.oseq from Order_view o where o.userid=:userid and o.result<>'4'  order by o.oseq desc";
        List<Integer> list = em.createQuery(sql, Integer.class)
                .setParameter("userid", userid).getResultList();
        return list;

    }

    public List<Integer> getOseqListAll(String userid) {
        String sql = "select distinct o.oseq from Order_view o where o.userid=:userid order by o.oseq desc";
        List<Integer> list = em.createQuery(sql, Integer.class)
                .setParameter("userid", userid).getResultList();
        return list;
    }

    public void purchase(int odseq) {
        Order_detail detail = em.find(Order_detail.class, odseq);
        detail.setResult("4");
    }
}
