package com.himedia.srshopserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Cart_view {
    @Id
    private Integer cseq;
    private String userid;
    private Integer pseq;
    private String mname;
    private String pname;
    private Integer quantity;
    private Integer price2;
    private Timestamp indate;
}
