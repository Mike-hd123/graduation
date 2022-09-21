package com.mike.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description 课程信息实体类
 * Author: Mike-hd123
 * Date2020/3/11 14:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  /**
   * id
   */
  private String id;
  /**
   * 课程名
   */
  private String name;
  /**
   * 学分
   */
  private Double credits;
  /**
   * 满分
   */
  private Integer score;
  /**
   * 课时
   */
  private Integer number;
  /**
   * 届时
   */
  private Integer year;
  /**
   * 学期
   */
  private Integer term;
  /**
   * 类型 1： 必修 2：选修
   */
  private Integer type;
  /**
   * 专业
   */
  private String profession;
  /**
   * 周数 start
   */
  private Integer start;
  /**
   * 周数 end
   */
  private Integer end;
  /**
   * 教室
   */
  private String room;
  /**
   * 创建时间
   */
  private Date createTime;
}
