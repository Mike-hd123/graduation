package com.mike.dto;

import lombok.Data;

@Data
public class ForgotPass {
    String code;
    String newPass;
}
