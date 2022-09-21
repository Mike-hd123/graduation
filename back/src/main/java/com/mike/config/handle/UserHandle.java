package com.mike.config.handle;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserHandle implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 通过所有OPTION请求
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        if (token == null) {
            PrintWriter out = response.getWriter();
            out.write(errortext("你还未登录!").toString());
            out.flush();
            out.close();
            return false;
        }

        if (redis.opsForValue().get(token) == null) {
            PrintWriter out = response.getWriter();
            out.write(errortext("token已经过期!").toString());
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    // 创建一个返回错误的对象
    private JSONObject errortext(String msg) {
        JSONObject res = new JSONObject();
        res.put("msg", msg);
        res.put("code", "400");
        res.put("data", "null");
        return res;
    }

}
