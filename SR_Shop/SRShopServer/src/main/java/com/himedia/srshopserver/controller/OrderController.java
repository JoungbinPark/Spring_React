package com.himedia.srshopserver.controller;

import com.himedia.srshopserver.dto.Order_v;
import com.himedia.srshopserver.entity.Member;
import com.himedia.srshopserver.entity.Order_view;
import com.himedia.srshopserver.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService os;

    @PostMapping("/insertOrders")
    public HashMap<String,Object> insertOrders(HttpServletRequest request){
        HashMap<String,Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginUser");
        int oseq = os.insertOrders( member.getUserid());
        result.put("oseq", oseq);
        System.out.println("oseq : " + oseq);

        return result;
    }

    @PostMapping("/insertOrderDetail")
    public HashMap<String, Object> insertOrderDetail(@RequestParam("oseq") int oseq, @RequestParam("cseq") int cseq){
        System.out.println("oseq, cseq : "+ oseq + " " + cseq);
        HashMap<String, Object> result = new HashMap<>();
        os.insertOrderDetail(oseq, cseq);

        return null;
    }

    @GetMapping("/getOrders/{oseq}")
    public HashMap<String,Object> getOrders(@PathVariable("oseq") int oseq){
        HashMap<String,Object> result = new HashMap<>();
        List<Order_view> list = os.getOrderByOseq( oseq );

        int totalPrice = 0;
        for (Order_view o : list){
            totalPrice += (o.getPrice2() * o.getQuantity());
        }

        Order_view orderDetail = list.get(0);

        result.put("list", list);
        result.put("totalPrice", totalPrice);
        result.put("orderDetail", orderDetail);
        return result;
    }

    @PostMapping("/insertOrderOne")
    public HashMap<String, Object> insertOrderOne(@RequestParam("pseq") int pseq, @RequestParam("userid") String userid, @RequestParam("quantity") int quantity){
        HashMap<String, Object> result = new HashMap<>();
        int oseq = os.insertOrderOne(pseq, userid, quantity);

        result.put("oseq", oseq);
        return result;
    }

    @GetMapping("/getOrderIng")
    public HashMap<String, Object> getOrderIng( HttpServletRequest request ) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        Member loginUser = (Member)session.getAttribute("loginUser");
        ArrayList<Order_v> list = os.getOrderIng(loginUser.getUserid());
        result.put("orderList", list);
        return result;
    }

    @GetMapping("/getOrderAll")
    public HashMap<String, Object> getOrderAll( HttpServletRequest request ) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        Member loginUser = (Member)session.getAttribute("loginUser");
        ArrayList<Order_v> list = os.getOrderAll(loginUser.getUserid());
        result.put("orderList", list);
        return result;
    }

    @PostMapping("/purchase")
    public HashMap<String, Object> purchase( @RequestParam("odseq") int odseq){
        HashMap<String, Object> result = new HashMap<>();
        os.purchase( odseq );
        return null;
    }

}
