package com.himedia.srboardServer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    private String email;
    private String pass;
    // @Column(name="subject")
    private String title;
    private String content;
    private String userid;
    private String image;
    private String savefilename;
    private int readcount;
    @CreationTimestamp
    private Date writedate;


}
