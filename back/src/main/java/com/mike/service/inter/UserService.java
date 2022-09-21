package com.mike.service.inter;

import java.util.List;

import com.mike.dao.entity.User;
import com.mike.dto.ChangePass;
import com.mike.dto.UserInfo;
import com.mike.util.MyError;

public interface UserService {

    // 登录
    public User login(UserInfo userInfo) throws MyError;

    // 获取头像
    public String getFile(int id);

    // 修改密码
    public boolean changePassword(ChangePass changePass) throws MyError;

    // 忘记密码
    public boolean forgotpassword(String mail, String newPass) throws MyError;

    // 启/禁账户
    public boolean enDisable(String id) throws MyError;

    // 更新头像
    public boolean upFile(String id, String path) throws MyError;

    // 注册
    public boolean registered(User user) throws MyError;

    public String SaveUser(User user);

    List<Object> getTree();

    Object getMe(String token);
}
