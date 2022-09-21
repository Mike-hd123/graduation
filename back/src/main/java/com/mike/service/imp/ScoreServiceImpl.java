package com.mike.service.imp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageRowBounds;
import com.mike.dao.ScoreDao;
import com.mike.dao.entity.Score;
import com.mike.service.inter.ScoreService;
import com.mike.util.PagingResult;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Description
 * Author: zjh
 * Date2020/3/13 15:49
 **/
@Service
public class ScoreServiceImpl implements ScoreService {
  @Autowired
  private ScoreDao scoreDao;

  @Autowired
  JavaMailSender mail;

  @Override
  public PagingResult<Score> getScoreList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Score> courseList = new ArrayList<>();
    switch (condition.get("type").toString()) {
      case "0":
        courseList = scoreDao.getScoreByAdmin(pageRowBounds, condition);
        for (Score course : courseList) {
          adminCourseMethod(course);
        }
        break;
      case "1":
        courseList = scoreDao.getScoreByMap(pageRowBounds, condition);
        break;
      case "2":
        courseList = scoreDao.getScoreByStudent(pageRowBounds, condition);
    }
    return new PagingResult<>(courseList, pageRowBounds.getTotal());
  }

  @Override
  public void addEntry(List<Score> list) {
    for (Score score : list) {
      // string转double
      double scoreByUser = Double.parseDouble(score.getScoreByUser());
      BigDecimal bg = new BigDecimal((scoreByUser / 10 - 5));
      // 取两位有效数字
      double f1 = bg.setScale(2, RoundingMode.HALF_UP).doubleValue();
      String point = scoreByUser > 59 ? String.valueOf(f1) : "0";
      String credits = scoreByUser >= score.getScore() * 0.6 ? score.getCredits() : "0.00";
      score.setPointByUser(point);
      score.setCreditsByUser(credits);
      score.setCourseId(Integer.toString(score.getId()));
      Map<String, Object> condition = new HashMap<>();
      condition.put("StudentId", score.getStudentId());
      condition.put("ScoreName", score.getName());
      Integer num = scoreDao.checkCount(condition);
      if (num == 0) {
        scoreDao.addEntry(score);
      } else {
        scoreDao.updateEntry(score);
      }
    }
  }

  @Override
  public List<Score> getExportList(Map<String, Object> condition) {
    List<Score> courseList = new ArrayList<>();
    switch (condition.get("type").toString()) {
      case "0":
        courseList = scoreDao.getExportListByAdmin(condition);
        for (Score course : courseList) {
          adminCourseMethod(course);
        }
        break;
      case "1":
        courseList = scoreDao.getExportList(condition);
        break;
      case "2":
        courseList = scoreDao.getExportListByStudent(condition);
    }
    return courseList;
  }

  @Override
  public List<Map<String, Object>> getUserNum(Map<String, Object> condition) {
    List<Map<String, Object>> list = new ArrayList<>();
    List<Score> courseList = new ArrayList<>();
    switch (condition.get("type").toString()) {
      case "0":
        courseList = scoreDao.getExportListByAdmin(condition);
        for (Score course : courseList) {
          adminCourseMethod(course);
        }
        list = dealScore(courseList);
        break;
      case "1":
        courseList = scoreDao.getExportList(condition);
        list = dealScore(courseList);
        break;
      case "2":
        courseList = scoreDao.getExportListByStudent(condition);
        list = dealScore(courseList);
    }
    return list;
  }

  @Override
  public Map<String, Object> getUserTotal(Map<String, Object> condition) {
    String type = condition.get("type").toString();
    if (type.equals("2")) {
      List<Score> list = scoreDao.getStudentTotal(condition);
      double credits = 0.00;
      double point = 0.00;
      for (Score course : list) {
        double a = Double.parseDouble(course.getCreditsByUser());
        credits += a;
        double b = Double.parseDouble(course.getPointByUser());
        point += b;
      }
      Map<String, Object> map = new HashMap<>();
      map.put("credits", credits);
      map.put("point", point);
      return map;
    } else if (type.equals("1")) {
      List<Score> courseList = scoreDao.getExportList(condition);
      return getLimit(courseList);
    } else {
      List<Score> courseList = scoreDao.getExportListByAdmin(condition);
      for (Score course : courseList) {
        adminCourseMethod(course);
      }
      return getLimit(courseList);
    }
  }

  private Map<String, Object> getLimit(List<Score> courseList) {
    List<Double> list = new ArrayList<>();
    double max = 0.00;
    double min = 0.00;
    double average = 0.00;
    double count = 0.00;
    for (Score course : courseList) {
      if (course.getScoreByUser() != null) {
        list.add(Double.parseDouble(course.getScoreByUser()));
      }
    }
    if (list.size() > 0) {
      Collections.sort(list);
      min = list.get(0);
      max = list.get(list.size() - 1);
    }

    for (Double score : list) {
      count += score;
    }
    average = list.size() == 0 ? 0.00 : count / list.size();
    Map<String, Object> map = new HashMap<>();
    map.put("max", max);
    map.put("min", min);
    map.put("average", average);
    return map;
  }

  private List<Map<String, Object>> dealScore(List<Score> courseList) {
    if (courseList.size() > 0) {
      Map<String, Object> unknownCondition = new HashMap<>();
      Map<String, Object> failCondition = new HashMap<>();
      Map<String, Object> passCondition = new HashMap<>();
      Map<String, Object> goodCondition = new HashMap<>();
      int fail = 0;
      int pass = 0;
      int good = 0;
      int unknown = 0;
      for (Score course : courseList) {
        double scoreFull = course.getScore() / 100;
        if (course.getScoreByUser() == null) {
          unknown++;
        } else {
          double score = Double.parseDouble(course.getScoreByUser()) * scoreFull;
          if (score < 60) {
            fail++;
          } else if (score < 85) {
            pass++;
          } else {
            good++;
          }
        }
      }
      int passLine = (int) (courseList.get(0).getScore() * 0.6);
      int goodLine = (int) (courseList.get(0).getScore() * 0.85);
      int FullLine = courseList.get(0).getScore();
      String unknownLabel = "未录入";
      String failLabel = "不及格(" + "0-" + (passLine - 1) + ")";
      String passLabel = "及格(" + passLine + "-" + (goodLine - 1) + ")";
      String goodLabel = "优秀(" + goodLine + "-" + FullLine + ")";
      if (unknown != 0) {
        unknownCondition.put("label", unknownLabel);
        unknownCondition.put("value", unknown);
      }
      failCondition.put("label", failLabel);
      failCondition.put("value", fail);
      passCondition.put("label", passLabel);
      passCondition.put("value", pass);
      goodCondition.put("label", goodLabel);
      goodCondition.put("value", good);
      List<Map<String, Object>> list = new ArrayList<>();
      list.add(goodCondition);
      list.add(passCondition);
      list.add(failCondition);
      list.add(unknownCondition);
      return list;
    } else {
      return new ArrayList<>();
    }
  }

  private void adminCourseMethod(Score course) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", course.getStudentId());
    map.put("courseName", course.getName());
    Score courseById = scoreDao.getScoreById(map);
    if (courseById != null) {
      course.setId(courseById.getId());
      course.setCreditsByUser(courseById.getCreditsByUser());
      course.setPointByUser(courseById.getPointByUser());
      course.setScoreByUser(courseById.getScoreByUser());
    }
  }
}
