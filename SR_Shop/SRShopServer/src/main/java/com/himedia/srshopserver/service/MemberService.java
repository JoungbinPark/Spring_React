package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.MemberDao;
import com.himedia.srshopserver.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MemberService {
    @Autowired
    MemberDao mdao;

    public Member getMember(String userid) {
        return mdao.getMember(userid);
    }

    public void insertMember(Member member) {
        mdao.insertMember(member);
    }
}
