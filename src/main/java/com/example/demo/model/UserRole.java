package com.example.demo.model;

import lombok.Data;

import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Table(name = "role")
public class UserRole {
    private Long id;
    private String name;
}
