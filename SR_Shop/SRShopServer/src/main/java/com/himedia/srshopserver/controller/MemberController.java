package com.himedia.srshopserver.controller;

import com.google.gson.Gson;
import com.himedia.srshopserver.dto.KakaoProfile;
import com.himedia.srshopserver.dto.OAuthToken;
import com.himedia.srshopserver.entity.Member;
import com.himedia.srshopserver.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    MemberService ms;

    @PostMapping("/locallogin")
    public HashMap<String, Object> localLogin(@RequestBody Member member, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Member mem = ms.getMember(member.getUserid());
        if(mem == null){
            result.put("msg", "해당 아이디가 없습니다.");
        } else if( !mem.getPwd().equals(member.getPwd())){
            result.put("msg", "패스워드가 틀립니다.");
        } else{
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", mem);
            result.put("msg", "ok");
        }
        return result;
    }

    @GetMapping("/getLoginUser")
    public HashMap<String, Object> getLoginUser(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        result.put("loginUser", session.getAttribute("loginUser"));
        return result;
    }

    @GetMapping("/logout")
    public HashMap<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return null;
    }

    @RequestMapping("/kakaostart")
    public @ResponseBody String kakaostart(){
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=4c345339fb66e95e889168ad69802a7b&"
                + "redirect_uri=http://localhost:8070/api/member/kakaoLogin&"
                + "&response_type=code';" + "</script>";
        return a;
    }

    @RequestMapping("/kakaoLogin")
    public void loginKakao(
            HttpServletRequest request,
            HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {

        String code = request.getParameter("code");
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint); // import java.net.URL;
        String bodyData = "grant_type=authorization_code&";
        bodyData += "client_id=4c345339fb66e95e889168ad69802a7b&";
        bodyData += "redirect_uri=http://localhost:8070/api/member/kakaoLogin&";
        bodyData += "code=" + code;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // import java.net.HttpURLConnection;
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input = "";
        StringBuilder sb = new StringBuilder(); // 조각난 String 을 조립하기위한 객체
        while ((input = br.readLine()) != null) {
            sb.append(input);
            //System.out.println(input); // 수신된 토큰을 콘솔에 출력합니다
        }
        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);

        HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
        String input2 = "";
        StringBuilder sb2 = new StringBuilder();
        while ((input2 = br2.readLine()) != null) {
            sb2.append(input2);
            //System.out.println(input2);
        }
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println("id : " + kakaoProfile.getId());
        System.out.println("KakaoAccount-Email : " + ac.getEmail());
        System.out.println("Profile-Nickname : " + pf.getNickname());

        Member member = ms.getMember( kakaoProfile.getId() );
        if( member == null) {
            member = new Member();
            member.setUserid( kakaoProfile.getId() );
            member.setEmail( pf.getNickname() );
            member.setName( pf.getNickname() );
            member.setProvider( "kakao" );

            ms.insertMember(member);
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", member);
        response.sendRedirect("http://localhost:3000/kakaosaveinfo");
    }

    @PostMapping("/idcheck")
    public HashMap<String, Object> idcheck(@RequestParam("userid") String userid){
        HashMap<String, Object> result = new HashMap<>();
        System.out.println("userid : " + userid);
        Member mem = ms.getMember(userid);
        if( mem == null){
            result.put("res", "1");
        } else {
            result.put("res", "0");
        }
        return result;
    }

    @PostMapping("/insertMember")
    public HashMap<String, Object> insertMember(@RequestBody Member member){
        HashMap<String, Object> result = new HashMap<>();
        ms.insertMember(member);
        result.put("msg", "ok");
        return result;
    }

}
