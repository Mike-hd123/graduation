package com.mike.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.mike.dao.entity.Score;
import com.mike.dto.Response;
import com.mike.service.inter.CourseService;
import com.mike.service.inter.ScoreService;
import com.mike.service.inter.TeachService;
import com.mike.service.inter.UserService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeachService teachService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    UserService userServer;

    // 获取账户树形结构
    @GetMapping("/getTree")
    public Response getTree() {
        Response response = new Response();
        response.setData(userServer.getTree());
        response.setCode(200);
        response.setMsg("ok!");
        return response;
    }

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

    @GetMapping("/getCourseInfo")
    public Response getCourseInfo(@RequestParam Map<String, Object> condition, HttpServletRequest request) {
        try {
            Response response = new Response();
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            condition.put("id", userValue.split(",")[2]);
            response.setData(teachService.getCourseInfo(condition));
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

    @GetMapping("/getProfessionInfoByTeacher")
    public Response getProfessionInfo(HttpServletRequest request) {
        try {
            Response response = new Response();
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            response.setData(teachService.getProfessionInfo(userValue.split(",")[2]));
            response.setMsg("ok!");
            response.setCode(200);
            return response;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    @PostMapping("/score")
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
}
