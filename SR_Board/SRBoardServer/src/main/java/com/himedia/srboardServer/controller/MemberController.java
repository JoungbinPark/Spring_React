package com.himedia.srboardServer.controller;


import com.himedia.srboardServer.entity.Member;
import com.himedia.srboardServer.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService ms;

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody Member member, HttpServletRequest request){
        System.out.println(member);
        HashMap<String, Object> result = new HashMap<>();
        Member mem = ms.getMember(member.getUserid());

        if (mem == null){
            result.put("msg", "아이디가 없습니다.");
        } else if ( !mem.getPwd().equals(member.getPwd())){
            result.put("msg", "패스워드가 일치되지 않습니다.");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", mem);
            result.put("msg", "ok");
        }
        return result;
    }

    @PostMapping("/join")
    public HashMap<String, Object> join(@RequestBody Member member, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        Member mem = ms.getMember(member.getUserid());
        if(mem != null){
            result.put("msg", "아이디가 중복됩니다.");
        } else {
            ms.insertMember( member );
            result.put("msg", "ok");
        }
        return result;
    }

    @GetMapping("/getLoginUser")
    public HashMap<String, Object> join( HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        result.put("loginUser", (Member) session.getAttribute("loginUser"));
        return result;
    }

    @GetMapping("/logout")
    public HashMap<String, Object> logout(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        result.put("msg", "ok");
        return result;
    }

    @PostMapping("/updateMember")
    public HashMap<String, Object> updateMember(@RequestBody Member member, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();

        // 전송된 자료로 멤버 수정. 이 떄 수정된 객체를 리턴받아 세션에 재저장
        Member mem = ms.updateMember( member);
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", mem);

        result.put("msg", "ok");
        return result;
    }

}
