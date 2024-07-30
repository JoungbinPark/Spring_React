package com.himedia.spServer;

import com.himedia.spServer.security.CustomSecurityConfig;
import jakarta.xml.bind.SchemaOutputResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringBootServerApplicationTests {

    @Autowired
    CustomSecurityConfig cc;

    @Test
    void contextLoads() {
        PasswordEncoder pe = cc.passwordEncoder();
        System.out.println("비밀번호를 바꾸자");
        System.out.println(pe.encode("a"));
    }

}
