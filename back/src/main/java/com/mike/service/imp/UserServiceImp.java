package com.mike.service.imp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.auth0.jwt.JWT;
import com.mike.dao.AdminDao;
import com.mike.dao.StudentDao;
import com.mike.dao.TeacherDao;
import com.mike.dao.UserDao;
import com.mike.dao.entity.Admin;
import com.mike.dao.entity.Student;
import com.mike.dao.entity.Teacher;
import com.mike.dao.entity.User;
import com.mike.dto.ChangePass;
import com.mike.dto.UserInfo;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Override
    public User login(UserInfo userInfo) throws MyError {
        if (userInfo.getUsername().trim() == "")
            throw new MyError("账号不能为空");

        if (userInfo.getPassword().trim() == "")
            throw new MyError("密码不能为空");

        User user = userDao.login(userInfo);

        if (user == null)
            throw new MyError("密码或用户名错误!");

        if (user.getDisable() == 1)
            throw new MyError("账户已被禁用，请联系管理员!");

        return user;
    }

    @Override
    public String getFile(int id) {
        return userDao.getFile(id);
    }

    @Override
    public boolean changePassword(ChangePass changePass) throws MyError {
        if (userDao.changePassword(changePass) <= 0)
            throw new MyError("原密码错误!");
        return true;
    }

    @Override
    public boolean forgotpassword(String email, String newPass) throws MyError {
        if (userDao.forgotpassword(email, newPass) <= 0)
            throw new MyError("请检查账号绑定的邮箱是否为:" + email);
        return true;
    }

    @Override
    public boolean enDisable(String id) throws MyError {
        if (userDao.enDisable(id) <= 0)
            throw new MyError("未知错误!");
        return true;
    }

    @Override
    public boolean registered(User user) throws MyError {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = Base64.getEncoder();
            user.setPassword(base64Encoder.encodeToString(md5.digest(user.getUser().getBytes("utf-8"))));
            user.setEmail("xxx@qq.com");
            user.setFile("123.jpg");
        } catch (UnsupportedEncodingException e) {
            throw new MyError(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new MyError(e.getMessage());
        }
        if (userDao.registered(user) <= 0)
            throw new MyError("未知错误!");
        return true;
    }

    @Override
    public String SaveUser(User user) {
        String token = "";
        token += user.getId() + ",";
        token += user.getType() + ",";
        token += user.getUser() + ",";
        token += System.currentTimeMillis();
        return token;
    }

    @Override
    public List<Object> getTree() {
        // 获取专业
        List<Student> studentList = studentDao.getStudentTree();
        Set<Student> studentSet = new HashSet<>(studentList);

        // 转化为前端树形结构所需的数据格式
        ArrayList<Object> list = new ArrayList<>();
        for (Student user : studentSet) {
            Map<String, Object> treeObj = new HashMap<>();
            ArrayList<Object> childrenList = new ArrayList<>();
            treeObj.put("label", user.getProfession());
            List<String> gradeList = studentDao.getGradeByProfession(user.getProfession());

            // 转成int，然后再排序
            List<Integer> list1 = new ArrayList<>();
            for (String str : new HashSet<>(gradeList)) {
                int number = Integer.parseInt(str);
                list1.add(number);
            }
            Collections.sort(list1);

            for (Integer i : list1) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("label", i.toString());
                childrenList.add(obj);
            }
            treeObj.put("children", childrenList);
            list.add(treeObj);
        }

        ArrayList<Object> treeList = new ArrayList<>();
        Map<String, Object> studentObj = new HashMap<>();
        Map<String, Object> teacherObj = new HashMap<>();
        Map<String, Object> adminObj = new HashMap<>();
        studentObj.put("label", "学生");
        studentObj.put("children", list);
        teacherObj.put("label", "教师");
        // teacherObj.put("children", list);
        adminObj.put("label", "管理员");
        treeList.add(studentObj);
        treeList.add(teacherObj);
        treeList.add(adminObj);
        return treeList;
    }

    @Override
    public Object getMe(String token) {
        String userValue = JWT.decode(token).getAudience().get(0);
        String id = userValue.split(",")[2];
        int type = Integer.parseInt(userValue.split(",")[1]);
        Object user = null;
        switch (type) {
            case 0:
                Admin admin = adminDao.getUserById(id);
                user = admin;
                break;
            case 1:
                Teacher teacher = teacherDao.getUserById(id);
                user = teacher;
                break;
            case 2:
                Student student = studentDao.getUserById(id);
                user = student;
                break;
        }
        return user;
    }

    @Override
    public boolean upFile(String id, String path) throws MyError {
        if (userDao.upFile(id, path) <= 0)
            throw new MyError("未知错误!");
        return true;
    }
}
