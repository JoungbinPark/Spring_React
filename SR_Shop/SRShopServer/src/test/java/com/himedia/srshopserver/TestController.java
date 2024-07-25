package com.himedia.srshopserver;

import com.himedia.srshopserver.entity.Admins;
import com.himedia.srshopserver.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestController {

    @Autowired
    AdminService as;


    @Test
    public void testMethod2(){
        Admins ad = as.getAdmins("admin");
        System.out.println(ad);
        if( ad == null){
            System.out.println("아이디 비번을 확인하세요");
        } else if(!ad.getPwd().equals("admin")){
            System.out.println("아이디 비번을 확인하세요");
        } else if(ad.getPwd().equals("admin")){
            System.out.println("OK");
        }
    }
}
