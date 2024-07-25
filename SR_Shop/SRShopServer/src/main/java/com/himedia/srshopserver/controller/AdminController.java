package com.himedia.srshopserver.controller;

import com.himedia.srshopserver.dto.Paging;
import com.himedia.srshopserver.entity.Admins;
import com.himedia.srshopserver.entity.Product;
import com.himedia.srshopserver.service.AdminService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService as;

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody Admins admins, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Admins ad = as.getAdmins( admins.getAdminid());
        if( ad == null){
            result.put("msg", "아이디 비번을 확인하세요");
        } else if(!ad.getPwd().equals(admins.getPwd())){
            result.put("msg", "아이디 비번을 확인하세요");
        } else if(ad.getPwd().equals(admins.getPwd())){
            HttpSession session = request.getSession();
            session.setAttribute("adminUser", ad);
            result.put("msg", "OK");
        }
        return result;
    }

    @GetMapping("/getProductList/{page}")
    public HashMap<String, Object> getProductList(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayRow(10);
        paging.calPaging();
        result.put("productList", as.getProductList(paging));
        result.put("paging", paging);
        return result;
    }

    @GetMapping("/getOrderList/{page}")
    public HashMap<String, Object> getOrderList(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayRow(10);
        paging.calPaging();
        result.put("orderList", as.getOrderList(paging));
        result.put("paging", paging);
        return result;
    }

    @GetMapping("/getQnaList/{page}")
    public HashMap<String, Object> getQnaList(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayRow(10);
        paging.calPaging();
        result.put("qnaList", as.getQnaList(paging));
        result.put("paging", paging);
        return result;
    }

    @GetMapping("/getMemberList/{page}")
    public HashMap<String, Object> getMemberList(@PathVariable("page") int page){
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayRow(10);
        paging.calPaging();
        result.put("memberList", as.getMemberList(paging));
        result.put("paging", paging);
        return result;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileup")
    public HashMap<String, Object> fileup(@RequestParam("image") MultipartFile file){
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/product_images");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();

        String filename = file.getOriginalFilename();
        String f1 = filename.substring(0, filename.indexOf("."));
        String f2 = filename.substring(filename.lastIndexOf("."));
        String uploadPath = path + "/" + f1 + dt + f2;

        try {
            file.transferTo(new File(uploadPath));
            result.put("savefilename", f1 + dt + f2);
            result.put("image", filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @PostMapping("/insertProduct")
    public HashMap<String, Object> insertProduct(@RequestBody Product product ){
        as.insertProduct(product);
        return null;
    }

    @GetMapping("/getProduct/{pseq}")
    public HashMap<String, Object> getProduct(@PathVariable("pseq") int pseq){
        HashMap<String, Object> result = new HashMap<>();
        Product product = as.getProduct(pseq);
        result.put("product", product);
        return result;
    }

    @PostMapping("/updateProduct")
    public HashMap<String, Object> updateProduct(@RequestBody Product product){
        HashMap<String, Object> result = new HashMap<>();
        as.updateProduct(product);
        return null;
    }

    @DeleteMapping("/deleteProduct/{pseq}")
    public HashMap<String, Object> deleteProduct(@PathVariable("pseq") int pseq){
        HashMap<String, Object> result = new HashMap<>();
        as.deleteProduct(pseq);
        return null;
    }

    @PostMapping("/nextResult")
    public HashMap<String, Object> nextResult(@RequestParam("odseq") int odseq){
        HashMap<String, Object> result = new HashMap<>();
        as.nextResult(odseq);

        return result;
    }

    @PostMapping("/changeUseyn")
    public HashMap<String, Object> changeUseyn(@RequestParam("userid") String userid, @RequestParam("useyn") String useyn){
        HashMap<String, Object> result = new HashMap<>();
        as.changeUseyn(userid, useyn);
        return null;
    }

    @PostMapping("/writeReply")
    public HashMap<String, Object> writeReply(@RequestParam("qseq") int qseq, @RequestParam("reply") String reply){
        HashMap<String, Object> result = new HashMap<>();
        as.writeReply(reply, qseq);
        return result;
    }

}
