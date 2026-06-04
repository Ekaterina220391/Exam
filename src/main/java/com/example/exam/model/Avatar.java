package com.example.exam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "avatars")
@Data
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String name;
}
