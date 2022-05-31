package com.mike.controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mike.dao.entity.User;
import com.mike.dto.ChangePass;
import com.mike.dto.ForgotPass;
import com.mike.dto.Response;
import com.mike.dto.UserInfo;
import com.mike.service.inter.UserService;
import com.mike.util.MyError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userServer;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    JavaMailSender mail;

    // 登录
    @PostMapping("login")
    public Response login(@RequestBody UserInfo userInfo) {
        try {
            User user = userServer.login(userInfo);
            // 设置用户信息到会话
            String userString = userServer.SaveUser(user);
            // 生成token
            String token = JWT.create()
                    .withAudience(userString)
                    .sign(Algorithm.HMAC256(userInfo.getPassword()));
            // 保存到redis中
            redis.opsForValue().set(token, user.getName(), 7, TimeUnit.DAYS);
            // 生成返回对象
            Response response = new Response();
            response.setMsg("ok!");
            response.setData(token);
            response.setCode(200);
            return response;
        } catch (MyError e) {
            return new Response(e.getMessage(), 300);
        }
    }

    // 修改密码
    @PutMapping("changePass")
    public Response changePass(@RequestBody ChangePass changePass, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            int id = Integer.parseInt(userValue.split(",")[0]);
            changePass.setId(id);
            userServer.changePassword(changePass);
            loginout(request);
            Response respons = new Response();
            respons.setMsg("密码以修改请重新登录!");
            respons.setCode(200);
            return respons;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    // 忘记密码
    @PutMapping("forgotPass")
    public Response forgotPass(@RequestBody ForgotPass forgotPass, HttpSession request, HttpServletRequest request2) {
        try {
            String email = request.getAttribute("ResEmail").toString();
            String code = request.getAttribute("code").toString();
            if (code == null || code.strip().equals(""))
                throw new MyError("请先获取输入验证码！");
            if (!code.strip().equals(forgotPass.getCode()))
                throw new MyError("验证码错误");
            userServer.forgotpassword(email, forgotPass.getNewPass());
            Response respons = new Response();
            respons.setMsg("密码已重置!");
            respons.setCode(200);
            return respons;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    // 获取验证码
    @GetMapping("getcode/{Email}")
    public Response getCode(@PathVariable("Email") String Email, HttpSession request) {
        try {
            String code = createCode();
            request.setAttribute("code", code);
            request.setAttribute("ResEmail", Email);
            // 发送验证码邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setText("你正在重置密码，你的验证码为:" + code);
            message.setSubject("重置密码");
            message.setFrom("2294618942@qq.com");
            message.setTo(Email);
            mail.send(message);
            Response respons = new Response();
            respons.setMsg("ok!");
            respons.setCode(200);
            return respons;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    // 注销登录
    @GetMapping("loginout")
    public Response loginout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        redis.delete(token);
        Response response = new Response();
        response.setCode(200);
        response.setMsg("login out ok.");
        return response;
    }

    // 获取头像
    @GetMapping("getFile")
    public Response getFile(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            String userValue = JWT.decode(token).getAudience().get(0);
            int id = Integer.parseInt(userValue.split(",")[0]);
            Response response = new Response();
            response.setMsg("ok!");
            response.setData(userServer.getFile(id));
            response.setCode(200);
            return response;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    @PostMapping("/upFile")
    public Response upload(MultipartFile file, HttpServletRequest request) {
        Response responce = new Response();
        String filename = null;
        if (!file.isEmpty()) {
            // 获取用户信息
            String token = request.getHeader("Authorization");
            // 设置文件保存路径
            filename = JWT.decode(token).getAudience().get(0).split(",")[0];
            // 保存文件
            try {
                file.transferTo(new File("/home/code/graduation/back/src/main/resources/static/static/img/" + filename
                        + file.getOriginalFilename()));
                userServer.upFile(filename, "/static/img/" + filename + file.getOriginalFilename());
            } catch (IOException e) {
                return new Response(e.getMessage(), 300);
            } catch (MyError e) {
                return new Response(e.getMessage(), 300);
            }
        }
        responce.setData("/static/img/" + filename + file.getOriginalFilename());
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

    // 获取个人信息
    @GetMapping("me")
    public Response getme(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            Response response = new Response();
            Object user = userServer.getMe(token);
            response.setData(user);
            response.setCode(200);
            response.setMsg("ok!");
            return response;
        } catch (Exception e) {
            return new Response(e.getMessage(), 300);
        }
    }

    private String createCode() {
        Integer a = 0;
        Random e = new Random();
        for (int i = 0; i < 6; i++) {
            a += e.nextInt(10);
            a = a * 10;
        }
        a = a / 10;
        return a.toString();
    }
}
