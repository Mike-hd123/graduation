package com.mike.service.inter;

import java.util.List;

import com.mike.dao.entity.Profession;

/**
 * Description 专业信息Service层
 * Author: Mike-hd123
 * Date2020/3/31 17:31
 **/
public interface ProfessionService {
  /**
   * description: 获取专业
   * return:
   * Author: Mike-hd123
   * 
   * @Date: 2020/3/31 17:31
   */
  List<Profession> getProfessionList();
}
