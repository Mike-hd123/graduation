package com.mike.dao.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
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
     * 学校
     */
    private String school;
    /**
     * 入学时间
     */
    private String admissionTime;
    /**
     * 专业
     */
    private String profession;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 班级
     */
    private String grade;
    /**
     * 创建时间
     */
    private Date createTime;
}
