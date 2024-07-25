package com.himedia.srshopserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Order_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odseq;
    private int oseq;
    private int pseq;
    private int quantity;
    private String result;
}
