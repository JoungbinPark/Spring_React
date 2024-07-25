package com.himedia.srshopserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order_view {

    @Id
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
