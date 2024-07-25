package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.AdminDao;
import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    AdminDao adao;

    public Admins getAdmins(String adminid) {
        return adao.getAdmins( adminid );
    }

    public List<Product> getProductList(Paging paging) {
        return adao.getProductList(paging);
    }

    public List<Order_view> getOrderList(Paging paging) {
        return adao.getOrderList(paging);
    }

    public List<Qna> getQnaList(Paging paging) {
        return adao.getQnaList(paging);

    }

    public List<Member> getMemberList(Paging paging) {
        return adao.getMemberList(paging);
    }

    public void insertProduct(Product product) {
        adao.insertProduct(product);
    }

    public Product getProduct(int pseq) {
        return adao.getProduct(pseq);
    }

    public void updateProduct(Product product) {
        adao.updateProduct(product);
    }

    public void deleteProduct(int pseq) {
        adao.deleteProduct(pseq);
    }

    public void nextResult(int odseq) {
        adao.nextResult( odseq );
    }

    public void changeUseyn(String userid, String useyn) {
        adao.changeUseyn(userid, useyn);
    }

    public void writeReply(String reply, int qseq) {
        adao.writeReply(reply, qseq);
    }
}
