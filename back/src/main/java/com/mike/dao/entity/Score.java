package com.mike.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 分数信息实体类
 * Author: Mike-hd123
 * Date2020/3/19 10:55
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
  /**
   * id
   */
  private Integer id;
  /**
   * 课程名
   */
  private String name;
  /**
   * 姓名
   */
  private String realName;
  /**
   * 课时
   */
  private Integer number;
  /**
   * 班级
   */
  private String grade;
  /**
   * 类型 1： 必修 2：选修
   */
  private Integer type;
  /**
   * 专业
   */
  private String profession;
  /**
   * 分数
   */
  private String scoreByUser;
  /**
   * 绩点
   */
  private String pointByUser;
  /**
   * 学分
   */
  private String creditsByUser;
  /**
   * 总分
   */
  private Integer score;
  /**
   * 学分
   */
  private String credits;
  /**
   * 学期
   */
  private Integer term;
  /**
   * 届时
   */
  private Integer year;
  /**
   * 学生账号id
   */
  private String studentId;
  /**
   * 课程id
   */
  private String courseId;
}
