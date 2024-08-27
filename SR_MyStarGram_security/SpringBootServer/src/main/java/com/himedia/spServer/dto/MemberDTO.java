package com.himedia.spserver.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemberDTO extends User {

    public MemberDTO( String username, String password, String email,  String phone,  String provider, String snsid, String profileimg, String profilemsg, List<String> roleNames) {
        super( username, password,
           roleNames.stream().map(
             str -> new SimpleGrantedAuthority("ROLE_"+str)
           ).collect(Collectors.toList())
           // ROLE_USER, ROLE_ADMIN, ROLE_MANAGER  와 같은 String 데이터 생성
        );
       // 생성자 전달된 전달인수들은 멤버변수에 저장
        this.nickname = username;
        this.pwd = password;
        this.email= email;
        this.phone = phone;
        this.provider = provider;
        this.snsid = snsid;
        this.profileimg = profileimg;
        this.profilemsg = profilemsg;
        this.roleNames = roleNames;

    }

    private String email;
    private String pwd;
    private String nickname;
    private String phone;
    private String provider;
    private String snsid;
    private String profileimg;
    private String profilemsg;
    private List<String> roleNames = new ArrayList<String>();


    // jwt 토큰 생성시에 그 안에 넣을 개인 정보들을  Map 형식으로 구성합니다
    // 암호화 jwt 토근 생성시에 그 Map을 통채로 암호화합니다
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("pwd",pwd);
        dataMap.put("nickname", nickname);
        dataMap.put("phone", phone);
        dataMap.put("provider", provider);
        dataMap.put("snsid", snsid);
        dataMap.put("profileimg", profileimg);
        dataMap.put("profilemsg", profilemsg);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }

}
