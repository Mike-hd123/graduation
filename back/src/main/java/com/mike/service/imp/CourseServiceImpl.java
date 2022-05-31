package com.mike.service.imp;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.CourseDao;
import com.mike.dao.CourseInfoDao;
import com.mike.dao.ProfessionDao;
import com.mike.dao.entity.Course;
import com.mike.dao.entity.CourseInfo;
import com.mike.dao.entity.Profession;
import com.mike.service.inter.CourseService;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 课程信息业务层
 * Author: Mike-hd123
 * Date2020/3/11 14:49
 **/
@Service
public class CourseServiceImpl implements CourseService {
  @Resource
  private CourseDao courseDao;
  @Resource
  private CourseInfoDao courseInfoDao;
  @Resource
  private ProfessionDao professionDao;

  @Override
  public void addCourse(Course course) {
    Profession profession = professionDao.getProfessionIdByName(course.getProfession());
    String id = "";
    String str = course.getYear().toString() + "0" + profession.getId() + course.getTerm();
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", course.getProfession());
    condition.put("year", course.getYear());
    String value = courseDao.checkCodeCount(condition);
    String strValue = "";
    if (value != null) {
      int num = Integer.parseInt(value.substring(7)) + 1;
      if (num < 10) {
        strValue = "00" + Integer.toString(num);
      } else if (num < 100) {
        strValue = "0" + Integer.toString(num);
      } else {
        strValue = Integer.toString(num);
      }
    } else {
      strValue = "001";
    }
    id = str + strValue;
    course.setId(id);
    courseDao.addCourse(course);

    courseInfoDao.deleteInfo(id);
    // 新增课程的周数等
    CourseInfo courseInfo = setCourseIfo(course);
    courseInfo.setCourseId(id);
    courseInfoDao.addCourseInfo(courseInfo);
  }

  @Override
  public void delete(List<Integer> ids) {
    courseDao.delete(ids);
    for (Integer id : ids) {
      courseInfoDao.deleteInfo(Integer.toString(id));
    }
  }

  @Override
  public void update(Course course) {
    courseDao.update(course);
    CourseInfo courseInfo = setCourseIfo(course);
    courseInfoDao.updateCourseInfo(courseInfo);
  }

  @Override
  public PagingResult<Course> getCourseList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Course> CourseList = courseDao.getCourseList(pageRowBounds, condition);
    return new PagingResult<>(CourseList, pageRowBounds.getTotal());
  }

  @Override
  public List<Course> getCourseByMap(Map<String, Object> condition) {
    return courseDao.getCourseByMap(condition);
  }

  private CourseInfo setCourseIfo(Course course) {
    CourseInfo courseInfo = new CourseInfo();
    courseInfo.setCourseId(course.getId());
    courseInfo.setStart(course.getStart());
    courseInfo.setEnd(course.getEnd());
    courseInfo.setRoom(course.getRoom());
    courseInfo.setProfession(course.getProfession());

    return courseInfo;
  }
}
