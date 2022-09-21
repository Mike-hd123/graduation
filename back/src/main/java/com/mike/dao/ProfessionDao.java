package com.mike.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.mike.dao.entity.Profession;

/**
 * Description 专业信息 Mapper层
 * Author: Mike-hd123
 * Date2020/3/31 17:34
 **/
@Mapper
public interface ProfessionDao {
  /**
   * description: 获取所有专业
   * return: List<Profession>
   * Author: Mike-hd123
   * 
   * @Date: 2020/3/31 17:34
   */
  List<Profession> getProfessionList();

  /**
   * description: 根据专业名查询专业数量（判断是否存在该专业）
   * 
   * @param: String
   *                return: Integer
   *                Author: Mike-hd123
   * @Date: 2020/3/31 17:35
   */
  Integer checkProfessionCount(@Param("name") String name);

  /**
   * description: 新增专业
   * 
   * @param: Profession
   *                    return: void
   *                    Author: Mike-hd123
   * @Date: 2020/3/31 17:36
   */
  void addProfession(Profession profession);

  /**
   * description: 删除专业
   * 
   * @param: Integer
   *                 return: void
   *                 Author: Mike-hd123
   * @Date: 2020/3/31 17:37
   */
  void deleteProfession(@Param("id") Integer id);

  /**
   * description: 根据专业名查询专业信息
   * 
   * @param: String
   *                return: Profession
   *                Author: Mike-hd123
   * @Date: 2020/3/31 17:52
   */
  Profession getProfessionIdByName(@Param("name") String name);
}
