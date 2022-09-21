package com.mike.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.mike.dao.CourseDao;
import com.mike.dao.ProfessionDao;
import com.mike.dao.StudentDao;
import com.mike.dao.TeachDao;
import com.mike.dao.entity.Course;
import com.mike.dao.entity.Profession;
import com.mike.dao.entity.Teach;
import com.mike.service.inter.TeachService;

/**
 * Description
 * Author: Mike-hd123
 * Date2020/3/29 15:09
 **/
@Service
public class TeachServiceImpl implements TeachService {

  @Autowired
  private TeachDao teachDao;
  @Autowired
  private ProfessionDao professionDao;
  @Autowired
  private StudentDao studentDao;
  @Autowired
  private CourseDao courseDao;

  @Override
  public void add(List<Teach> list) {
    List<Integer> ids = new ArrayList<>();
    for (Teach teacherCourse : list) {
      if (teacherCourse.getId() != null) {
        ids.add(teacherCourse.getId());
      }
    }
    if (ids.size() > 0) {
      teachDao.delete(ids);
    }
    for (Teach teacherCourse : list) {
      Course course = courseDao.getCourseById(teacherCourse.getCourseId());
      teacherCourse.setName(course.getName());
      teachDao.add(teacherCourse);
    }
  }

  @Override
  public void delete(List<Integer> ids) {
    teachDao.delete(ids);
  }

  @Override
  public void update(Teach teacherCourse) {
    teachDao.update(teacherCourse);
  }

  @Override
  public List<Teach> getCourseListById(String id) {
    return teachDao.getCourseListById(id);
  }

  @Override
  public List<Map<String, Object>> getProfessionInfo(String teacherId) {
    List<Teach> list = teachDao.getCourseListById(teacherId);
    List<Map<String, Object>> arr = new ArrayList<>();
    Set<String> professionSet = new HashSet<>();
    for (Teach teacherCourse : list) {
      professionSet.add(teacherCourse.getProfession());
    }
    for (String s : professionSet) {
      Map<String, Object> condition = new HashMap<>();
      condition.put("teacherId", teacherId);
      condition.put("profession", s);
      List<Teach> listObj = teachDao.getGradeInfoByMap(condition);
      Set<String> gradeSet = new HashSet<>();
      Set<String> courseSet = new HashSet<>();
      for (Teach teacherCourse : listObj) {
        gradeSet.add(teacherCourse.getGrade());
        courseSet.add(teacherCourse.getName());
      }
      condition.put("grade", gradeSet);
      condition.put("course", courseSet);
      arr.add(condition);
    }
    return arr;
  }

  @Override
  public List<Map<String, Object>> getProfessionInfoByAdmin() {
    List<Map<String, Object>> arr = new ArrayList<>();
    List<Profession> professionList = professionDao.getProfessionList();
    for (Profession profession : professionList) {
      Map<String, Object> condition = new HashMap<>();
      condition.put("profession", profession.getName());
      List<String> gradeList = studentDao.getGradeByProfession(profession.getName());
      List<Course> courseList = courseDao.getCourseByMap(condition);
      List<String> courseNameList = new ArrayList<>();
      for (Course course : courseList) {
        courseNameList.add(course.getName());
      }

      // 转成int，然后再排序
      List<Integer> list = new ArrayList<>();
      for (String str : new HashSet<>(gradeList)) {
        int number = Integer.parseInt(str);
        list.add(number);
      }
      Collections.sort(list);

      condition.put("grade", list);
      condition.put("course", new HashSet<>(courseNameList));
      arr.add(condition);
    }
    return arr;
  }

  @Override
  public Teach getCourseInfo(Map<String, Object> condition) {
    return teachDao.getCourseInfo(condition);
  }
}
