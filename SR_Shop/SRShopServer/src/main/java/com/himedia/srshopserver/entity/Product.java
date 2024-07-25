package com.himedia.srshopserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int pseq;
    private String name;
    private String kind;
    private int price1;
    private int price2;
    private int price3;
    private String content;
    private String image;
    private String savefilename;
    private String bestyn;
    private String useyn;
    @CreationTimestamp
    private Timestamp indate;
}
