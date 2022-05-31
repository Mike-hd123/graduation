package com.mike.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@PropertySource(value = "classpath:mail.properties")
@Configuration
public class Email {
    @Autowired
    Environment environment;

    /**
     * Spring邮件配置
     */
    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(environment.getProperty("mail.host"));
        javaMailSenderImpl.setUsername(environment.getProperty("mail.username"));
        javaMailSenderImpl.setPassword(environment.getProperty("mail.password"));
        javaMailSenderImpl.setDefaultEncoding(environment.getProperty("mail.encoding"));
        return javaMailSenderImpl;
    }
}
