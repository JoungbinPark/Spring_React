package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    EntityManager em;

    public Admins getAdmins(String adminid) {
        return em.find(Admins.class, adminid);
    }

    public List<Product> getProductList(Paging paging) {
        String sql = "select p from Product p order by p.pseq desc";
        List<Product> list = em.createQuery(sql, Product.class)
                .setFirstResult( paging.getStartNum()-1)
                .setMaxResults( paging.getDisplayRow())
                .getResultList();
        return list;
    }

    public List<Order_view> getOrderList(Paging paging) {
        String sql = "select o from Order_view o order by o.odseq desc";
        List<Order_view> list = em.createQuery(sql, Order_view.class)
                .setFirstResult( paging.getStartNum()-1)
                .setMaxResults( paging.getDisplayRow())
                .getResultList();
        return list;
    }

    public List<Qna> getQnaList(Paging paging) {
        String sql = "select q from Qna q order by q.qseq desc";
        List<Qna> list = em.createQuery(sql, Qna.class)
                .setFirstResult( paging.getStartNum()-1)
                .setMaxResults( paging.getDisplayRow())
                .getResultList();
        return list;
    }

    public List<Member> getMemberList(Paging paging) {
        String sql = "select m from Member m order by m.indate desc";
        List<Member> list = em.createQuery(sql, Member.class)
                .setFirstResult(paging.getStartNum()-1)
                .setMaxResults(paging.getDisplayRow())
                .getResultList();
        return list;
    }

    public void insertProduct(Product product) {
        em.persist(product);
    }

    public Product getProduct(int pseq) {
        return em.find(Product.class, pseq);
    }

    public void updateProduct(Product product) {
        Product p = em.find(Product.class, product.getPseq());
        p.setKind(product.getKind());
        p.setName(product.getName());
        p.setUseyn(product.getUseyn());
        p.setBestyn(product.getBestyn());
        p.setPrice1(product.getPrice1());
        p.setPrice2(product.getPrice2());
        p.setPrice3(product.getPrice3());
        p.setContent(product.getContent());
        p.setImage(product.getImage());
        p.setSavefilename(product.getSavefilename());

    }

    public void deleteProduct(int pseq) {
        Product p = em.find(Product.class, pseq);
        em.remove(p);
    }

    public void nextResult(int odseq) {
        Order_detail detail = em.find(Order_detail.class, odseq);
        int result = Integer.parseInt(detail.getResult());
        if (result < 3){
            result = result+1;
        }
        detail.setResult(result+"");
    }

    public void changeUseyn(String userid, String useyn) {
        Member member = em.find(Member.class, userid);
        member.setUseyn(useyn);
    }

    public void writeReply(String reply, int qseq) {
        Qna qna = em.find(Qna.class, qseq);
        qna.setReply(reply);
    }
}
