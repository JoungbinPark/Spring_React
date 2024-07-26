package com.himedia.spServer.service;

import com.himedia.spServer.dao.FollowRepository;
import com.himedia.spServer.dao.LikesRepository;
import com.himedia.spServer.dao.MemberRepository;
import com.himedia.spServer.entity.Follow;
import com.himedia.spServer.entity.Likes;
import com.himedia.spServer.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository mr;

    @Autowired
    FollowRepository fr;

    public Member getMember(String email) {
        // Optional : 검색결과가 null이어서 발생할 수 있는 예외처리나 에러를 방지하기 위한 자바의 도구. null 값이 있을지도 모를 객체를 감싸서 null 인데 접근하려는 것을 사전에 차단합니다.다음과 같이 검증을 거친 후 사용되어집니다.
        Optional<Member> mem = mr.findByEmail( email);
        // isPresent() : 해당 객체가 인스턴스를 저장하고 있다면  true, null 이면 false를 리턴
        // isEmpty() : isPresent()의 반대값을 리턴합니다.
        if(!mem.isPresent()){
            return null;
        } else{
            // get() : Optional 내부 객체를 꺼내서 리턴
            return mem.get();
        }
    }

    public Member getMemberByNickname(String nickname) {
        Optional<Member> mem = mr.findByNickname(nickname);
        if(!mem.isPresent()){
            return null;
        } else {
            return mem.get();
        }
    }

    public List<Follow> getFollowings(String nickname) {
        List<Follow> list = fr.findByFfrom(nickname);
        return list;
    }

    public List<Follow> getFollowers(String nickname) {
        List<Follow> list = fr.findByFto(nickname);
        return list;
    }

    public Member getMemberBySnsid(String id) {
        Optional<Member> mem = mr.findBySnsid( id );
        if(!mem.isPresent()){
            return null;
        }else{
            return mem.get();
        }
    }

    public void insertMember(Member member) {
        mr.save(member);
    }

    public void onFollow(String ffrom, String fto) {
        // ffrom 과 fto로 전달된 값으로 레코드가 있는지 검사
        Optional<Follow> rec = fr.findByFfromAndFto(ffrom, fto);
        if(( !rec.isPresent()) ){
            Follow f = new Follow();
            f.setFfrom(ffrom);
            f.setFto(fto);
            fr.save(f);
        }
    }


}
