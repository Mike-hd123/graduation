package com.mike.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.TeacherDao;
import com.mike.dao.entity.Teacher;
import com.mike.dao.entity.User;
import com.mike.service.inter.TeacherService;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description 教师用户业务层
 * Author: Mike-hd123
 * Date2020/3/7 15:10
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

  @Resource
  private TeacherDao teacherDao;

  @Autowired
  private UserService userService;

  @Override
  public void addTeacher(Teacher teacher) throws MyError {
    int num = teacherDao.checkCodeCount();
    String str = "";
    if (num < 10) {
      str = "00" + Integer.toString(num);
    } else if (num < 100) {
      str = "0" + Integer.toString(num);
    } else {
      str = Integer.toString(num);
    }
    String no = "389" + str + teacher.getSex().toString();
    teacher.setId(no);
    teacherDao.addTeacher(teacher);
    User user = getUser(teacher);
    user.setType(1);
    userService.registered(user);
  }

  @Override
  public void delete(List<Integer> ids) {
    teacherDao.delete(ids);
  }

  @Override
  public void update(Teacher user) {
    teacherDao.update(user);
  }

  @Override
  public PagingResult<Teacher> getTeacherList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Teacher> TeacherInfoList = teacherDao.getTeacherList(pageRowBounds, condition);
    return new PagingResult<>(TeacherInfoList, pageRowBounds.getTotal());
  }

  private User getUser(Teacher teacher) {
    User user = new User();
    user.setName(teacher.getId());
    user.setEmail(teacher.getEmail());
    user.setType(0);
    user.setUser(teacher.getId());
    user.setFile("123.jpg");
    user.setDisable(0);
    return user;
  }
}
