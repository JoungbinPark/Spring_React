package com.himedia.srshopserver.dao;

import com.himedia.srshopserver.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @Autowired
    EntityManager em;

    public Member getMember(String userid) {
        return em.find(Member.class, userid);
    }

    public void insertMember(Member member) {
        em.persist(member);
    }
}
