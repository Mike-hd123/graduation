package com.mike.dao;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.entity.Teacher;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 教师用户mapper层
 * Author: Mike-hd123
 * Date2020/3/7 15:11
 **/
@Mapper
public interface TeacherDao {

  /**
   * description: 新增教师账号信息
   * 
   * @param Teacher
   * @return void
   * @author Mike-hd123
   * @date 2020/3/4 23:05
   */
  void addTeacher(Teacher Teacher);

  /**
   * description: 删除教师账号
   *
   * @param ids
   * @return void
   * @author Mike-hd123
   * @date 2019/8/29 14:55
   */
  void delete(@Param("ids") List<Integer> ids);

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
   * @author Mike-hd123
   * @return java.util.List<com.mike.dao.entity.Teacher1>
   * @date 2020/3/4 23:05
   */
  List<Teacher> getTeacherList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);

  /**
   * description:
   * 
   * @param:
   * return:
   *                 Author: Mike-hd123
   * @Date: 2020/3/30 23:43
   */
  Teacher getUserById(@Param("id") String id);

  /**
   * description: 查看人数
   * return: Integer
   * Author: Mike-hd123
   * 
   * @Date: 2020/3/11 15:03
   */
  Integer checkCodeCount();
}
