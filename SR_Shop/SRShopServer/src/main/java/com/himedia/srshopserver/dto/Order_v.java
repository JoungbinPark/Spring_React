package com.himedia.srshopserver.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order_v {
    private int odseq;
    private int oseq;
    private String userid;
    private Timestamp indate;
    private int pseq;
    private int quantity;
    private String mname;
    private String zip_num;
    private String address1;
    private String address2;
    private String phone;
    private String pname;
    private int price2;
    private String result;
}
