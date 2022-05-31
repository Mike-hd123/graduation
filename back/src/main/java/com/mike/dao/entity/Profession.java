package com.mike.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 专业实体类
 * Author: Mike-hd123
 * Date2020/3/31 17:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profession {
  /**
   * 专业id
   */
  private Integer id;
  /**
   * 专业名
   */
  private String name;
}
