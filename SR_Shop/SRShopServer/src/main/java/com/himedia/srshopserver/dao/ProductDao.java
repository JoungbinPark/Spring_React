package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    EntityManager em;

    public List<Product> getBestProduct() {
        String sql = "select p from Product p where p.bestyn=:bestyn ";
        List<Product> list = em.createQuery(sql, Product.class)
                .setParameter("bestyn", "Y")
                .setFirstResult(0)
                .setMaxResults(4)
                .getResultList();
        return list;
    }

    public List<Product> getNewProduct() {
        String sql = "select p from Product p order by p.indate desc";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        query.setFirstResult(0);
        query.setMaxResults(4);
        List<Product> list = query.getResultList();

        return list;
    }
}