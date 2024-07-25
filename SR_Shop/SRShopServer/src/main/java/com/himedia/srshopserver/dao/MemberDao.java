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

    public Member updateMember(Member member) {
        Member mem = em.find(Member.class, member.getUserid() );
        mem.setName(member.getName());
        mem.setEmail(member.getEmail());
        mem.setPhone(member.getPhone());
        mem.setZip_num(member.getZip_num());
        mem.setAddress1(member.getAddress1());
        mem.setAddress2(member.getAddress2());
        mem.setAddress3(member.getAddress3());
        mem.setPwd(member.getPwd());
        mem.setProvider(member.getProvider());
        return mem;

    }

    public void withdrawal(String userid) {
        Member mem = em.find(Member.class, userid);
        mem.setUseyn("N");
    }
}
