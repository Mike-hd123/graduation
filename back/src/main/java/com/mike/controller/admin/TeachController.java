package com.mike.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mike.dao.entity.Teach;
import com.mike.dto.Response;
import com.mike.service.inter.TeachService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description 教师课程控制层
 * Author: Mike-hd123
 * Date2020/3/29 15:03
 **/
@RestController
@RequestMapping("/api/admin/teach")
public class TeachController {

  @Autowired
  private TeachService teachService;

  @PostMapping
  public Response add(@RequestBody JSONArray teacherCourseInfo) {
    Response response = new Response();
    try {
      List<Teach> list = JSONObject.parseArray(teacherCourseInfo.toJSONString(), Teach.class);
      teachService.add(list);
      response.setMsg("ok!");
      response.setCode(200);
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
    return response;
  }

  @DeleteMapping("/{ids}")
  public Response delete(@PathVariable("ids") Integer[] ids, HttpSession request) {
    Response response = new Response();
    try {
      List<Integer> idsList = Arrays.asList(ids);
      teachService.delete(idsList);
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @PutMapping
  public Response update(@RequestBody Teach teacherCourse, HttpSession request) {
    Response response = new Response();
    try {
      teachService.update(teacherCourse);
      response.setMsg("ok!");
      response.setCode(200);
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
    return response;
  }

  @GetMapping("/getCourseListById/{id}")
  public Response getCourseListById(@PathVariable("id") String id) {
    try {
      Response response = new Response();
      response.setData(teachService.getCourseListById(id));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getProfessionInfoByAdmin")
  public Response getProfessionInfoByAdmin() {
    try {
      Response response = new Response();
      response.setData(teachService.getProfessionInfoByAdmin());
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getProfessionInfoByTeacher/{teacherId}")
  public Response getProfessionInfo(@PathVariable("teacherId") String teacherId) {
    try {
      Response response = new Response();
      response.setData(teachService.getProfessionInfo(teacherId));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getCourseInfo")
  public Response getCourseInfo(@RequestParam Map<String, Object> condition) {
    try {
      Response response = new Response();
      response.setData(teachService.getCourseInfo(condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }
}
