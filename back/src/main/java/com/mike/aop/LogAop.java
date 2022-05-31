package com.mike.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

// @Aspect
@Slf4j
// @Component
public class LogAop {
    @Pointcut("execution(* com.mike.controller.*.*(..))")
    public void webLog() {

    }

    /**
     * @Description: 前置通知--接口请求信息
     **/
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 收到请求,记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String url = request.getRequestURL().toString();
            // 记录请求url
            log.info("URL: " + url);
            // 记录请求方法
            log.info("REQUEST: " + request.getMethod());
            log.info("REQUEST_METHOD: " + joinPoint.getSignature());
            // 获取请求参数
            if (url.indexOf("upFile") == -1)
                requestParam(joinPoint);
            // 记录请求IP
            log.info("IP: " + getIpAddress(request));
        }
    }

    /**
     * @Description: 请求参数
     **/
    public void requestParam(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        // 构造参数组集合
        Map<Object, Object> map = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            // request/response无法使用toJSON
            if (args[i] instanceof HttpServletRequest) {
                map.put(JSON.toJSON(parameterNames[i]), "request");
            } else if (args[i] instanceof HttpServletResponse) {
                map.put(JSON.toJSON(parameterNames[i]), "response");
            } else {
                map.put(JSON.toJSON(parameterNames[i]), JSON.toJSON(args[i]));
            }
        }
        log.info("REQUEST_PARAM: {}", JSON.toJSON(map));
    }

    /**
     * @Description: 环绕通知--统计接口耗时 强制加入环绕 正式环境可以取消
     **/
    // @Around("webLog()")
    // public Object around(ProceedingJoinPoint point) throws Throwable {
    // // 接口时长
    // long start = System.currentTimeMillis();
    // Object result = point.proceed();
    // long end = System.currentTimeMillis();
    // long elapsedTime = start - end;
    // log.info("INTERFACE_ELAPSED_TIME : {} ms", elapsedTime);
    // return result;
    // }

    /**
     * @Description: 后置通知--接口返回参数
     **/
    // @AfterReturning(returning = "returnValue", pointcut = "webLog()")
    // public void doAfterReturning(Object returnValue) throws
    // JsonProcessingException {
    // JSONObject responseParam = JSON.parseObject(new
    // ObjectMapper().writeValueAsString(returnValue));
    // // 处理完请求，返回内容
    // log.info("RESPONSE_PARAM : {}", responseParam);
    // }

    /**
     * @Description: 异常通知--打印异常信息
     **/
    @AfterThrowing(value = "webLog()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Exception exception) {
        // 收到请求,记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 记录请求url
            log.info("URL: {}", request.getRequestURL().toString());
            // 记录请求方法
            log.info("REQUEST: {}", request.getMethod());
            log.info("REQUEST_METHOD: {}", joinPoint.getSignature());
            // 记录请求IP
            log.info("IP: {}", getIpAddress(request));
            // 异常信息
            log.info("EXCEPTION: {}", exception.getMessage());
        }
    }

    /**
     * @Description: 获取目标主机的ip
     **/
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
