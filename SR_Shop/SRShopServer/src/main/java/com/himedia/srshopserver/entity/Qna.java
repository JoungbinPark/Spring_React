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
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qseq;
    private String subject;
    private String content;
    private String reply;
    private String userid;
    private String security;
    private String pass;
    @CreationTimestamp
    private Timestamp indate;
}
