package com.himedia.srboardServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class UserController {

    @GetMapping("/getWord")
    public HashMap<String, Object> getWord(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("word", "Hello World");

        return result;
    }


}
