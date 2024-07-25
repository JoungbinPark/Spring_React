package com.himedia.srshopserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer cseq;
    private String userid;
    private int pseq;
    private int quantity;
    @CreationTimestamp
    private Timestamp indate;
}
