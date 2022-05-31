package com.mike.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mike.dao.entity.Course;
import com.mike.dto.Response;
import com.mike.service.inter.CourseService;

import org.apache.ibatis.session.RowBounds;
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
 * Description 课程控制层
 * Author: Mike-hd123
 * Date2020/3/11 14:40
 **/
@RestController
@RequestMapping("/api/admin/course")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @PostMapping
  public Response addCourse(@RequestBody Course course) {
    try {
      courseService.addCourse(course);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @DeleteMapping("/{ids}")
  public Response delete(@PathVariable("ids") Integer[] ids) {
    try {
      List<Integer> idsList = Arrays.asList(ids);
      courseService.delete(idsList);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @PutMapping
  public Response update(@RequestBody Course course) {
    try {
      courseService.update(course);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getCourseList")
  private Response getCourseList(@RequestParam Map<String, Object> condition,
      @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
      @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    try {
      RowBounds rowBounds = new RowBounds(offset, limit);
      Response response = new Response();
      response.setData(courseService.getCourseList(rowBounds, condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getCourseByMap")
  private Response getCourseByMap(@RequestParam Map<String, Object> condition) {
    try {
      Response response = new Response();
      response.setData(courseService.getCourseByMap(condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }
}
