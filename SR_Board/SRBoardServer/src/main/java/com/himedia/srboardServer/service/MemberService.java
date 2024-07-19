package com.himedia.srboardServer.service;

import com.himedia.srboardServer.dao.MemberDao;
import com.himedia.srboardServer.entity.Member;
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
        mdao.insertMember( member );
    }

    public Member updateMember(Member member) {
        return mdao.updateMember(member);
    }
}
