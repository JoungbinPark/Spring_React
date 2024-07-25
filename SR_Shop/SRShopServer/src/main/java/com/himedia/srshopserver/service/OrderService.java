package com.himedia.srshopserver.service;

import com.himedia.srshopserver.dao.OrderDao;
import com.himedia.srshopserver.dto.Order_v;
import com.himedia.srshopserver.entity.Cart;
import com.himedia.srshopserver.entity.Order_detail;
import com.himedia.srshopserver.entity.Order_view;
import com.himedia.srshopserver.entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderDao odao;


    public int insertOrders(String userid) {
        Orders orders = new Orders();
        orders.setUserid(userid);
        odao.insertOrders(orders);

        int oseq = odao.lookupMaxOseq();

        return oseq;
    }

    public void insertOrderDetail(int oseq, int cseq) {
        Cart cart = odao.getCart( cseq );
        Order_detail order_detail = new Order_detail();
        order_detail.setOseq(oseq);
        order_detail.setPseq(cart.getPseq());
        order_detail.setQuantity(cart.getQuantity());
        order_detail.setResult("1");

        odao.insertOrderDetail( order_detail );

        odao.deleteCart( cart );
    }

    public List<Order_view> getOrderByOseq(int oseq) {
        return odao.getOrderByOseq( oseq );
    }

    public int insertOrderOne(int pseq, String userid, int quantity) {
        Orders orders = new Orders();
        // order 테이블에 레코드 추가
        orders.setUserid(userid);
        odao.insertOrders(orders);
        // oseq 조회
        int oseq = odao.lookupMaxOseq();
        // order_detail 레코드 추가
        Order_detail order_detail = new Order_detail();
        order_detail.setOseq(oseq);
        order_detail.setPseq(pseq);
        order_detail.setQuantity(quantity);
        order_detail.setResult("1");
        odao.insertOrderDetail(order_detail);
        // oseq 리턴
        return oseq;

    }

    public ArrayList<Order_v> getOrderIng(String userid) {
        // oseq 조회
        ArrayList<Order_v> list = new ArrayList<Order_v>();
        List<Integer> oseqList = odao.getOseqListIng( userid );

        for(int oseq: oseqList){
            // oseq로 주문 조회
            List<Order_view> orderListIng = odao.getOrderByOseq(oseq);
            // 정리된 주문내역이 저장될 dto 생성(Entity X)
            Order_v orderV = new Order_v();
            // 주문상품들의 첫번째 상품명과 주문상품의 개수를 이용하여 dto 상품명 변경
            orderV.setPname( orderListIng.get(0).getPname() + " 포함 " + orderListIng.size() + " 건 ");
            // 총금액을 이용해서 dto의 price2 변경
            int totalPrice = 0;
            for( Order_view o : orderListIng)
                totalPrice += o.getPrice2() * o.getQuantity();
            orderV.setPrice2(totalPrice);
            // oseq, indate도 dto에 저장
            orderV.setOseq(orderListIng.get(0).getOseq());
            orderV.setIndate(orderListIng.get(0).getIndate());
            // dto를 리스트에 저장
            list.add(orderV);
        }
        return list;
    }

    public ArrayList<Order_v> getOrderAll(String userid) {
        ArrayList<Order_v> list = new ArrayList<Order_v>();
        List<Integer> oseqList = odao.getOseqListAll( userid );

        for(int oseq: oseqList){
            List<Order_view> orderListIng = odao.getOrderByOseq(oseq);
            Order_v orderV = new Order_v();
            orderV.setPname( orderListIng.get(0).getPname() + " 포함 " + orderListIng.size() + " 건 ");
            int totalPrice = 0;
            for( Order_view o : orderListIng)
                totalPrice += o.getPrice2() * o.getQuantity();
            orderV.setPrice2(totalPrice);
            orderV.setOseq(orderListIng.get(0).getOseq());
            orderV.setIndate(orderListIng.get(0).getIndate());
            list.add(orderV);
        }
        return list;
    }

    public void purchase(int odseq) {
        odao.purchase(odseq);
    }
}
