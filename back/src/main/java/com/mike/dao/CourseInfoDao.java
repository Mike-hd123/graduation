package com.mike.dao;

import com.mike.dao.entity.CourseInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description 课程具体安排表
 * Author: Mike-hd123
 * Date2020/5/3 14:57
 **/
@Mapper
public interface CourseInfoDao {
  /**
   * description: 新增课程具体安排（周数等）
   * 
   * @param: courseInfo
   *                    return: void
   *                    Author: Mike-hd123
   * @Date: 2020/5/3 14:57
   */
  void addCourseInfo(CourseInfo courseInfo);

  /**
   * description: 修改课程具体安排（周数等）
   * 
   * @param: courseInfo
   *                    return: void
   *                    Author: Mike-hd123
   * @Date: 2020/5/3 14:57
   */
  void updateCourseInfo(CourseInfo courseInfo);

  /**
   * description: 删除课程具体安排
   * 
   * @param: String
   *                return: void
   *                Author: Mike-hd123
   * @Date: 2020/5/3 16:23
   */
  void deleteInfo(@Param("id") String id);
}
