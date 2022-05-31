package com.mike.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.mike.dto.Response;
import com.mike.service.inter.CourseService;
import com.mike.service.inter.ScoreService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/getScoreList")
    public Response getScoreList(@RequestParam Map<String, Object> condition,
            @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset,
            HttpServletRequest request) {
        try {
            RowBounds rowBounds = new RowBounds(offset, limit);
            Response response = new Response();
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            condition.put("type", userValue.split(",")[1]);
            condition.put("id", userValue.split(",")[2]);
            response.setData(scoreService.getScoreList(rowBounds, condition));
            response.setMsg("ok!");
            response.setCode(200);
            return response;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    @GetMapping("/export")
    public Response getExportList(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        try {
            Response response = new Response();
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            condition.put("type", userValue.split(",")[1]);
            condition.put("id", userValue.split(",")[2]);
            response.setData(scoreService.getExportList(condition));
            response.setMsg("ok!");
            response.setCode(200);
            return response;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    @GetMapping("/getUserNum")
    public Response getUserNum(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        try {
            Response response = new Response();
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            condition.put("type", userValue.split(",")[1]);
            condition.put("id", userValue.split(",")[2]);
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
            condition.put("id", userValue.split(",")[2]);
            response.setData(scoreService.getUserTotal(condition));
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
}
