package com.himedia.srshopserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Admins {
    @Id
    private String adminid;
    private String pwd;
    private String name;
    private String phone;
}
