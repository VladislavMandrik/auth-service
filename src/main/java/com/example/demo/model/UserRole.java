package com.example.demo.model;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "roles")
public class UserRole {
    private Long id;
    private String name;
}
