package com.himedia.srshopserver.controller;

import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.Qna;
import com.himedia.srshopserver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService cs;

    @GetMapping("/qnalist/{page}")
    public HashMap<String, Object> qnalist(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();

        Paging paging = new Paging();
        paging.setPage( page );
        paging.calPaging();

        List<Qna> list = cs.getQnalist( paging );
        result.put("qnalist", list);
        result.put("paging", paging);
        return result;
    }

    @PostMapping("/writeqna")
    public HashMap<String, Object> writeqna(@RequestBody Qna qna){
        cs.writeQna( qna );
        return null;
    }

    @GetMapping("/getQna/{qseq}")
    public HashMap<String, Object> getQna(@PathVariable("qseq") int qseq){
        HashMap<String, Object> result = new HashMap<>();
        Qna qna = cs.getQna(qseq);
        result.put("qna", qna);

        return result;
    }

    @PostMapping("/passcheck")
    public HashMap<String, Object> passcheck(@RequestParam("qseq") int qseq, @RequestParam("inputPass") String inputPass){
            HashMap<String, Object> result = new HashMap<>();
            Qna qna = cs.getQna(qseq);
            if( qna.getPass().equals(inputPass)){
                result.put("msg", "OK");
            } else{
                result.put("msg", "FAIL");
            }
            return result;
    }


}
