package com.mike.config;

import com.mike.config.handle.AdminHandle;
import com.mike.config.handle.StudentHandle;
import com.mike.config.handle.TearcherHandle;
import com.mike.config.handle.UserHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    UserHandle userHandle;

    @Autowired
    AdminHandle adminHandle;

    @Autowired
    TearcherHandle tearcherHandle;

    @Autowired
    StudentHandle studentHandle;

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截
        registry.addInterceptor(userHandle).addPathPatterns("/api/**").excludePathPatterns("/api/user/login",
                "/api/user/getcode/**", "/api/user/forgotPass");
        registry.addInterceptor(adminHandle).addPathPatterns("/api/admin/**");
        registry.addInterceptor(tearcherHandle).addPathPatterns("/api/teacher/**");
        registry.addInterceptor(studentHandle).addPathPatterns("/api/student/**");
    }
}
