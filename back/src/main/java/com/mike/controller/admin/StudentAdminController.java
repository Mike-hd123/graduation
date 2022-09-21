package com.mike.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mike.dao.entity.Student;
import com.mike.dto.Response;
import com.mike.service.inter.StudentService;

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
 * Description 学生账号控制层
 * Author: Mike-hd123
 * Date2020/3/7 11:50
 **/
@RestController
@RequestMapping("/api/admin/student")
public class StudentAdminController {
  @Autowired
  private StudentService studentService;

  @PostMapping
  public Response addStudent(@RequestBody Student user) {
    try {
      studentService.addStudent(user);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @DeleteMapping("/{ids}")
  public Response delete(@PathVariable("ids") String[] ids) {
    try {
      List<String> idsList = Arrays.asList(ids);
      studentService.delete(idsList);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @PutMapping
  public Response update(@RequestBody Student user) {
    try {
      studentService.update(user);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getStudentList")
  public Response getStudentList(@RequestParam Map<String, Object> condition,
      @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
      @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    try {
      RowBounds rowBounds = new RowBounds(offset, limit);
      Response response = new Response();
      response.setData(studentService.getStudentList(rowBounds, condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }
}
