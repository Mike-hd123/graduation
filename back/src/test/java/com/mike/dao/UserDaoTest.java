package com.mike.dao;

import com.mike.BackApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { BackApplication.class })
public class UserDaoTest {

    @Autowired
    UserDao userDao;

}
