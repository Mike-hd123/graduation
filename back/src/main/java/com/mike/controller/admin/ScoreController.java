package com.mike.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.mike.dao.entity.Score;
import com.mike.dto.Response;
import com.mike.service.inter.ScoreService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description 成绩查询控制层
 * Author: Mike-hd123
 * Date2020/3/13 15:25
 **/

@RestController
@RequestMapping("/api/admin/score")
public class ScoreController {

  @Autowired
  private ScoreService scoreService;

  @GetMapping("/getScoreList")
  public Response getScoreList(@RequestParam Map<String, Object> condition,
      @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
      @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    try {
      RowBounds rowBounds = new RowBounds(offset, limit);
      Response response = new Response();
      condition.put("type", "0");
      response.setData(scoreService.getScoreList(rowBounds, condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/export")
  public Response getExportList(@RequestParam Map<String, Object> condition) {
    try {
      Response response = new Response();
      condition.put("type", "0");
      response.setData(scoreService.getExportList(condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getUserNum")
  public Response getUserNum(@RequestParam Map<String, Object> condition) {
    try {
      Response response = new Response();
      condition.put("type", "0");
      response.setData(scoreService.getUserNum(condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @GetMapping("/getUserTotal")
  public Response getUserTotal(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
    try {
      Response response = new Response();
      String token = request.getHeader("Authorization");
      String userValue = JWT.decode(token).getAudience().get(0);
      condition.put("type", userValue.split(",")[1]);
      response.setData(scoreService.getUserTotal(condition));
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

  @PostMapping
  private Response addEntry(@RequestBody JSONArray UserScore) {
    Response response = new Response();
    try {
      List<Score> list = JSONObject.parseArray(UserScore.toJSONString(), Score.class);
      scoreService.addEntry(list);
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }

}
