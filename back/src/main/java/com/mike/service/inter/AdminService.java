package com.mike.service.inter;

import java.util.List;
import java.util.Map;

import com.mike.dao.entity.Admin;
import com.mike.util.MyError;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;

/**
 * Description 管理员Service层
 * Author: Mike-hd123
 * Date2020/3/28 11:05
 **/
public interface AdminService {
  /**
   * description: 新增学生账号
   * 
   * @param Admin
   * @return void
   * @author Mike-hd123
   * @date 2020/3/4 23:05
   */
  void add(Admin Admin) throws MyError;

  /**
   * description: 删除学生账号
   *
   * @param ids
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void delete(List<String> ids);

  /**
   * description: 修改学生账号
   *
   * @param Admin
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void update(Admin Admin);

  /**
   * description: 获取学生账号信息列表
   * 
   * @param rowBounds
   * @param condition
   * @author Mike-hd123
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<Admin> getAdminList(RowBounds rowBounds, Map<String, Object> condition);
}
