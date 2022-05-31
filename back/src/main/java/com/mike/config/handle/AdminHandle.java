package com.mike.config.handle;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminHandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String userValue = JWT.decode(token).getAudience().get(0);
        String type = userValue.split(",")[1];
        if (!type.equals("0")) {
            PrintWriter out = response.getWriter();
            out.write(errortext("无权限!").toString());
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
        res.put("code", "500");
        res.put("data", "null");
        return res;
    }
}
