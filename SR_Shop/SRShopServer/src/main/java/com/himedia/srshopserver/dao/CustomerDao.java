package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.Qna;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao {

    @Autowired
    EntityManager em;

    public List<Qna> getQnalist(Paging paging) {
        String sql = "select q from Qna q order by q.qseq desc";
        List<Qna> list = em.createQuery(sql, Qna.class)
                .setFirstResult( paging.getStartNum()-1)
                .setMaxResults( paging.getDisplayRow())
                .getResultList();
        return list;
    }

    public void writeQna(Qna qna) {
        em.persist(qna);
    }

    public Qna getQna(int qseq) {
        return em.find(Qna.class, qseq);
    }
}
