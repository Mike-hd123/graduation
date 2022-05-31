package com.mike.dao;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.entity.Admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 管理员账号Mapper层
 * Author: Mike-hd123
 * Date2020/3/28 11:08
 **/
@Mapper
public interface AdminDao {
  /**
   * description: 新增管理员账号信息
   * 
   * @param Admin
   * @return void
   * @author Mike-hd123
   * @date 2020/3/4 23:05
   */
  void add(Admin Admin);

  /**
   * description: 删除管理员账号
   *
   * @param ids
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void delete(@Param("ids") List<String> ids);

  /**
   * description: 修改管理员账号
   *
   * @param Admin
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void update(Admin Admin);

  /**
   * description: 获取管理员账号信息列表
   * 
   * @param rowBounds
   * @author Mike-hd123
   * @return java.util.List<com.mike.dao.entity.Admin1>
   * @date 2020/3/4 23:05
   */
  List<Admin> getAdminList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);

  /**
   * description: 根据管理员id获取管理员信息
   * 
   * @param: String
   *                return: Admin
   *                Author: Mike-hd123
   * @Date: 2020/3/30 23:43
   */
  Admin getUserById(@Param("id") String id);

  /**
   * description: 查看管理员人数
   * return: Integer
   * Author: Mike-hd123
   * 
   * @Date: 2020/3/11 15:03
   */
  Integer checkCodeCount();
}
