package com.himedia.srboardServer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Data
@Entity
public class Member {
    @Id
    private String userid;

    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String provider;

    @CreationTimestamp
    private Date indate;

}
