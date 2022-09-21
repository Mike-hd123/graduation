package com.mike.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mike.dao.entity.Admin;
import com.mike.dto.Response;
import com.mike.service.inter.AdminService;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;

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
 * Description 管理员账户控制层
 * Author: Mike-hd123
 * Date2020/3/28 11:05
 **/
@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;

  @Autowired
  UserService userServer;

  @PostMapping
  public Response addAdmin(@RequestBody Admin user) {
    try {
      adminService.add(user);
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
      adminService.delete(idsList);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @PutMapping
  public Response update(@RequestBody Admin user) {
    try {
      adminService.update(user);
      Response response = new Response();
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getAdminList")
  public Response getAdminList(@RequestParam Map<String, Object> condition,
      @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
      @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    try {
      Response response = new Response();
      RowBounds rowBounds = new RowBounds(offset, limit);
      response.setData(adminService.getAdminList(rowBounds, condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  // 获取账户树形结构
  @GetMapping("/getTree")
  public Response getTree() {
    Response response = new Response();
    response.setData(userServer.getTree());
    response.setCode(200);
    response.setMsg("ok!");
    return response;
  }

  // 启/禁账户
  @GetMapping("disable/{id}")
  public Response enDisable(@PathVariable("id") String id) {
    Response response = new Response();
    try {
      userServer.enDisable(id);
      response.setCode(200);
      response.setMsg("ok!");
    } catch (MyError e) {
      return new Response(e.getMessage(), 300);
    }
    return response;
  }

}
