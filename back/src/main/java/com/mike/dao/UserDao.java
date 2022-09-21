package com.mike.dao;

import com.mike.dao.entity.User;
import com.mike.dto.ChangePass;
import com.mike.dto.UserInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getUserById(int id);

    @Select("select * from user where name=#{username} and password=#{password} and disable=0")
    User login(UserInfo user);

    @Select("select file from user where id=#{id}")
    String getFile(int id);

    @Update("update user set password=#{newpass} where id=#{id} and password=#{oldpass}")
    int changePassword(ChangePass changePass);

    @Update("update user set password=#{newPass} where email=#{email}")
    int forgotpassword(String email, String newPass);

    @Update("update user set disable = abs(disable-1) where user = #{id}")
    int enDisable(String id);

    @Insert("Insert into user(name,password,type,user,file,disable)  values(#{name},#{password},#{type},#{user},#{file},#{disable})")
    int registered(User user);

    @Update("update user set file = #{file} where id = #{id}")
    int upFile(String id, String file);
}
