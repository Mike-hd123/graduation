package com.mike.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.mike.BackApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootTest(classes = { BackApplication.class })
public class EmailTest {
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Test
    void testJavaMailSenderImpl() throws MessagingException {
        MimeMessage message = javaMailSenderImpl.createMimeMessage();

        // 发送带附件和内联元素的邮件需要将第二个参数设置为true
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // 发送方邮箱，和配置文件中的mail.username要一致
        helper.setFrom("2294618942@qq.com");

        // 接收方
        helper.setTo("Mike-hd123@qq.com");

        // 主题
        helper.setSubject("邮件测试");

        // 邮件内容
        helper.setText("邮件测试", true);

        javaMailSenderImpl.send(message);
    }
}
