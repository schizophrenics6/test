package com.example.demo.controller;
import com.example.demo.maapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Strings;

@RestController
public class R {
@Resource
JavaMailSender sender;
     @PostMapping("/code")
    public String code(@RequestParam String email, HttpSession session)
     {
         String randomString = "asdfasda";
             session.setAttribute("email", email);
             session.setAttribute("code",randomString);
         SimpleMailMessage message = new SimpleMailMessage();
             message.setSubject("你的验证码");
             message.setText(randomString);
             message.setTo(email); // 设置邮件发送给谁，可以多个
             message.setFrom("17537709956@163.com"); // 邮件发送者，这里要与配置文件中的保持一致
             sender.send(message);
             System.out.println("发送成功");
             return "发送成功";
     }

@Resource
    UserMapper mapper;
     @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String code,
                           @RequestParam String password,
                           HttpSession session)
     {
         String sessionCode = (String) session.getAttribute("code");
         String sessionEmail = (String) session.getAttribute("email");
// 验证码是否为空
         if (sessionCode == null) {
             return "请先获取验证码";
         }
// 验证用户提交的验证码是否与会话中的验证码一致
         if (!sessionCode.equals(code)) {
             return "验证码不正确";
         }
// 验证用户提交的电子邮件地址是否与会话中的电子邮件地址一致
         if (!sessionEmail.equals(email)) {
             return "请先获取验证码";
         }
//删
         //mapper.deleteUserByEmail(email);
//  创建用户
         mapper.createUser(username,password,email);
         System.out.println("注册成功");
         return "注册成功";
     }
}

