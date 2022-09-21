package com.mike.service.inter;

import java.util.List;
import java.util.Map;

import com.mike.dao.entity.Teacher;
import com.mike.util.MyError;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;

/**
 * Description 教师用户接口
 * Author: Mike-hd123
 * Date2020/3/7 15:05
 **/
public interface TeacherService {
  /**
   * description: 新增教师账号
   * 
   * @param Teacher
   * @return void
   * @author Mike-hd123
   * @date 2020/3/4 23:05
   */
  void addTeacher(Teacher Teacher) throws MyError;

  /**
   * description: 删除教师账号
   *
   * @param ids
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void delete(List<Integer> ids);

  /**
   * description: 修改教师账号
   *
   * @param Teacher
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void update(Teacher Teacher);

  /**
   * description: 获取教师账号信息列表
   * 
   * @param rowBounds
   * @param condition
   * @author Mike-hd123
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<Teacher> getTeacherList(RowBounds rowBounds, Map<String, Object> condition);
}
