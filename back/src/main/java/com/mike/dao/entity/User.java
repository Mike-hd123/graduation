package com.mike.dao.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

// 用户
@Data
public class User {
    @Id
    int id;
    String name;
    String password;
    int type;
    String email;
    String user;
    String file;
    int disable;
}