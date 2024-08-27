package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String ffrom;
    private String fto;

}
