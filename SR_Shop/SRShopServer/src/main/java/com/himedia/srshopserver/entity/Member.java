package com.himedia.srshopserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
public class Member {
    @Id
    private String userid;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private String zip_num;
    private String address1;
    private String address2;
    private String address3;
    @CreationTimestamp
    private Timestamp indate;
    @ColumnDefault("Y")
    private String useyn;
    private String provider;



}
