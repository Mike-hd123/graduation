package com.mike.dao.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    /**
     * id
     */
    private String id;
    /**
     * 前端用于展示不同的界面
     */
    private int type;
    /**
     * 账号是否禁用
     */
    private boolean state;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
}
