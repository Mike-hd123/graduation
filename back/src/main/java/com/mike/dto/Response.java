package com.mike.dto;

import lombok.Data;

@Data
public class Response {
    Object Data;
    int code;
    String msg;

    public Response(String message, int i) {
        this.msg = message;
        code = i;
    }

    public Response() {
    };
}