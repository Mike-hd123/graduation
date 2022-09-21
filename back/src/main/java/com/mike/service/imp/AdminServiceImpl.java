package com.mike.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.AdminDao;
import com.mike.dao.entity.Admin;
import com.mike.dao.entity.User;
import com.mike.service.inter.AdminService;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 * Author: Mike-hd123
 * Date2020/3/28 11:06
 **/
@Service
public class AdminServiceImpl implements AdminService {

  @Resource
  private AdminDao adminDao;

  @Autowired
  private UserService userService;

  @Override
  public void add(Admin admin) throws MyError {
    int num = adminDao.checkCodeCount() + 1;
    String Username;
    if (num < 10)
      Username = "10000";
    else
      Username = "1000";
    admin.setId(Username + Integer.toString(num));
    adminDao.add(admin);
    User user = getUser(admin);
    user.setType(0);
    userService.registered(user);
  }

  @Override
  public void delete(List<String> ids) {
    adminDao.delete(ids);
  }

  @Override
  public void update(Admin Admin) {
    adminDao.update(Admin);
  }

  @Override
  public PagingResult<Admin> getAdminList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Admin> StudentInfoList = adminDao.getAdminList(pageRowBounds, condition);
    return new PagingResult<>(StudentInfoList, pageRowBounds.getTotal());
  }

  private User getUser(Admin admin) {
    User user = new User();
    user.setName(admin.getId());
    user.setEmail(admin.getEmail());
    user.setType(0);
    user.setUser(admin.getId());
    user.setFile("123.jpg");
    user.setDisable(0);
    return user;
  }
}
