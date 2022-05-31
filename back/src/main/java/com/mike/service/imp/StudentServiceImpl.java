package com.mike.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.ProfessionDao;
import com.mike.dao.StudentDao;
import com.mike.dao.entity.Profession;
import com.mike.dao.entity.Student;
import com.mike.dao.entity.User;
import com.mike.service.inter.StudentService;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description 学生用户业务层
 * Author: Mike-hd123
 * Date2020/3/7 15:09
 **/
@Service
public class StudentServiceImpl implements StudentService {

  @Resource
  private StudentDao studentDao;
  @Autowired
  private ProfessionDao professionDao;
  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public void addStudent(Student Student) throws MyError {
    int professionId = 0;
    int count = professionDao.checkProfessionCount(Student.getProfession());
    if (count > 0) {
      Profession profession = professionDao.getProfessionIdByName(Student.getProfession());
      professionId = profession.getId();
    } else {
      Profession profession = new Profession();
      profession.setName(Student.getProfession());
      professionDao.addProfession(profession);
      professionId = profession.getId();
    }
    String professionStr = "";
    if (professionId < 10) {
      professionStr = "0" + Integer.toString(professionId);
    } else {
      professionStr = Integer.toString(professionId);
    }
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", Student.getProfession());
    condition.put("grade", Student.getGrade());
    int num = studentDao.checkCodeCount(condition) + 1;
    String str = "";
    if (num < 10) {
      str = "0" + Integer.toString(num);
    } else if (num < 100) {
      str = Integer.toString(num);
    }
    String no = "3" + Student.getAdmissionTime().substring(Student.getAdmissionTime().length() - 2)
        + "89" + professionStr + Student.getGrade().substring(Student.getGrade().length() - 1) + str;
    Student.setId(no);
    studentDao.addStudent(Student);
    User user = getUser(Student);
    user.setType(2);
    userService.registered(user);
  }

  @Override
  public void delete(List<String> ids) {
    studentDao.delete(ids);
  }

  @Override
  public void update(Student User) {
    studentDao.update(User);
  }

  @Override
  public PagingResult<Student> getStudentList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Student> StudentInfoList = studentDao.getStudentList(pageRowBounds, condition);
    return new PagingResult<>(StudentInfoList, pageRowBounds.getTotal());
  }

  private User getUser(Student student) {
    User user = new User();
    user.setName(student.getId());
    user.setEmail(student.getEmail());
    user.setType(2);
    user.setUser(student.getId());
    user.setFile("123.jpg");
    user.setDisable(0);
    return user;
  }
}
