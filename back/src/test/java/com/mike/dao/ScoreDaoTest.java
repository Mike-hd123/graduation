package com.mike.dao;

import java.util.HashMap;
import java.util.Map;

import com.mike.BackApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { BackApplication.class })
public class ScoreDaoTest {

    @Autowired
    ScoreDao scoreDao;

    @Test
    void testAddEntry() {
    }

    @Test
    void testCheckCount() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("StudentId", "3168901101");
        condition.put("CourseName", "数字电路基础");
    }

    @Test
    void testGetExportList() {
        System.out.println(scoreDao.getEmailByStudentId("3890001"));
    }

    @Test
    void testGetExportListByAdmin() {

    }

    @Test
    void testGetExportListByStudent() {

    }

    @Test
    void testGetScoreByAdmin() {

    }

    @Test
    void testGetScoreById() {

    }

    @Test
    void testGetScoreByMap() {

    }

    @Test
    void testGetScoreByStudent() {

    }

    @Test
    void testGetStudentTotal() {

    }

    @Test
    void testUpdateEntry() {

    }
}
