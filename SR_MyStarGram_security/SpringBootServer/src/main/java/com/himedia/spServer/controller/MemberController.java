package com.himedia.spserver.controller;

import com.google.gson.Gson;
import com.himedia.spserver.dto.KakaoProfile;
import com.himedia.spserver.dto.OAuthToken;
import com.himedia.spserver.entity.Follow;
import com.himedia.spserver.entity.Member;
import com.himedia.spserver.security.CustomSecurityConfig;
import com.himedia.spserver.security.util.CustomJWTException;
import com.himedia.spserver.security.util.JWTUtil;
import com.himedia.spserver.service.MemberService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
@Log4j2
public class MemberController {

    @Autowired
    MemberService ms;


    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @RequestMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + client_id + "&"
                + "redirect_uri=" + redirect_uri + "&"
                + "response_type=code';" + "</script>";
        return a;
    }

    @Autowired
    CustomSecurityConfig cc;

    @RequestMapping("/kakaoLogin")
    public void loginKakao( HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);
        String bodyData = "grant_type=authorization_code&";
        bodyData += "client_id=f67ebc2de23039bbce25c7d2583abd81&";
        bodyData += "redirect_uri=http://localhost:8070/member/kakaoLogin&";
        bodyData += "code=" + code;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input = "";
        StringBuilder sb = new StringBuilder();
        while ((input = br.readLine()) != null) {
            sb.append(input);
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
            System.out.println(input2);
        }
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println("id : " + kakaoProfile.getId());
        System.out.println("KakaoAccount-Email : " + ac.getEmail());
        System.out.println("Profile-Nickname : " + pf.getNickname());
        Member member = ms.getMemberBySnsid( kakaoProfile.getId() );
        if( member == null) {
            member = new Member();
            //member.setEmail( pf.getNickname() );
            member.setEmail( ac.getEmail() );  // 전송된 이메일이 없으면 pf.getNickname()
            member.setNickname( pf.getNickname() );
            member.setProvider( "kakao" );
            PasswordEncoder pe = cc.passwordEncoder();  // 비밀번호 암호화 도구
            member.setPwd(  pe.encode("kakao") );
            member.setSnsid( kakaoProfile.getId() );
            ms.insertMember(member);
        }
        //HttpSession session = request.getSession();
        //session.setAttribute("loginUser", member);
        String nick = URLEncoder.encode(pf.getNickname(),"UTF-8");
        response.sendRedirect("http://localhost:3000/kakaosaveinfo/" + nick);
    }

    @Autowired
    ServletContext context;
    @PostMapping("/fileupload")
    public HashMap<String, Object> fileup( @RequestParam("image") MultipartFile file){

        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/uploads");

        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf(".") );
        String fn2 = filename.substring(filename.indexOf(".") );
        String uploadPath = path + "/" + fn1 + dt + fn2;

        try {
            file.transferTo( new File(uploadPath) );
            result.put("filename", fn1 + dt + fn2);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/emailcheck")
    public HashMap<String, Object> emailcheck( @RequestParam("email") String email ){
        HashMap<String, Object> result = new HashMap<String, Object>();
        Member mem = ms.getMember( email );
        if( mem != null ) result.put("msg", "no");
        else result.put("msg", "yes");
        return result;
    }

    @PostMapping("/nicknamecheck")
    public HashMap<String, Object> nicknamecheck( @RequestParam("nickname") String nickname){
        HashMap<String, Object> result = new HashMap<String, Object>();
        Member mem = ms.getMemberByNickname( nickname );
        if( mem != null ) result.put("msg", "no");
        else result.put("msg", "yes");
        return result;
    }


    @PostMapping("/join")
    public HashMap<String, Object> join( @RequestBody Member member){
        HashMap<String, Object> result = new HashMap<String, Object>();
        PasswordEncoder pe = cc.passwordEncoder();
        member.setPwd( pe.encode(member.getPwd()) );
        ms.insertMember(member);
        result.put("msg", "ok");
        return result;
    }

    @PostMapping("/follow")
    public HashMap<String, Object> follow( @RequestParam("ffrom") String ffrom, @RequestParam("fto") String fto){
        ms.onFollow( ffrom, fto );
        return null;
    }

    @PostMapping("/unfollow")
    public HashMap<String, Object> unfollow( @RequestParam("ffrom") String ffrom, @RequestParam("fto") String fto){
        ms.onUnFollow( ffrom, fto );
        return null;
    }

    @GetMapping("/getFollowings")
    public List<Follow> getFollowings( @RequestParam("nickname") String nickname ){
        List<Follow> list = ms.getFollowings( nickname );
        return list;
    }

    @GetMapping("/getFollowers")
    public List<Follow> getFollowers( @RequestParam("nickname") String nickname ){
        List<Follow> list = ms.getFollowers( nickname );
        return list;
    }


    @PostMapping("/updateProfile")
    public HashMap<String, Object> updateProfile(@RequestBody Member member, HttpServletRequest request){

        HashMap<String, Object> result = new HashMap<String, Object>();

        PasswordEncoder pe = cc.passwordEncoder();
        member.setPwd( pe.encode(member.getPwd()) );

        ms.updateProfile( member );
        //HttpSession session = request.getSession();
        //session.setAttribute("loginUser", member );

        result.put("msg", "ok");
        result.put("loginUser", member);

        return result;
    }


    @GetMapping("/getMemberInfo/{membernick}")
    public HashMap<String, Object> getMemeberInfo( @PathVariable("membernick") String membernick){
        HashMap<String, Object> result = new HashMap<>();
        Member member = ms.getMemberByNickname(membernick);
        result.put("cuser", member);

        List<Follow> followinglist  = ms.getFollowings( membernick );
        List<Follow> followerlist  = ms.getFollowers( membernick );
        result.put("followings", followinglist);
        result.put("followers", followerlist);

        return result;
    }


    @GetMapping("/refresh/{refreshToken}")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("refreshToken") String refreshToken
    ) throws CustomJWTException {

        if( refreshToken == null ) throw new CustomJWTException("NULL_REFRASH");
        if( authHeader == null || authHeader.length() < 7 )
            throw new CustomJWTException("INVALID_HEADER");

        //추출한 내용의 7번째 글자부터 끝까지 추출
        String accessToken = authHeader.substring(7);

        if( checkExpiredToken( accessToken ) ){  // 기간이 지나면 false, 안지났으면  true 리턴
            log.info("그데로 사용");
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }else{

            // accessToken 기간 만료시  refresh 토큰으로 재 검증하여 사용자 정보 추출
            Map<String, Object> claims = JWTUtil.validateToken(refreshToken);


            // 토큰 교체
            String newAccessToken = JWTUtil.generateToken(claims, 1);
            String newRefreshToken = "";
            //if(  checkTime( (Integer)claims.get("exp") )  )
            newRefreshToken = JWTUtil.generateToken(claims, 60*24);
            //else
            //    newRefreshToken = refreshToken;

            return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
        }
    }

    private boolean checkTime(Integer exp) {
        java.util.Date expDate = new java.util.Date( (long)exp * (1000 ));//밀리초로 변환
        long gap = expDate.getTime() - System.currentTimeMillis();//현재 시간과의 차이 계산
        long leftMin = gap / (1000 * 60); //분단위 변환
        //1시간도 안남았는지..
        return leftMin < 60;
    }


    private boolean checkExpiredToken(String accessToken) {
        try{
            JWTUtil.validateToken( accessToken );
        }catch( CustomJWTException ex ){
            if( ex.getMessage().equals("Expired")) return false;
        }
        return true;
    }


}
