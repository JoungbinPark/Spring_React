package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String writer;
    @CreationTimestamp
    private Timestamp writedate;

}
