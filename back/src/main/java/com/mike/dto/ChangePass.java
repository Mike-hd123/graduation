package com.mike.dto;

import lombok.Data;

@Data
public class ChangePass {
    int id;
    String oldpass;
    String newpass;
}
