package com.example.demo.model;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "role")
public class UserRole {
    private Long id;
    private String name;
}
