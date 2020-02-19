package com.example.demo.controller;

import com.example.demo.service.ManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    ManagerService manager;


    @PostMapping("/toLogin")
    public String toLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(required = false) String remember,
                          HttpSession session,
                          Model model){

        //获得当前用户
        Subject subject = SecurityUtils.getSubject();
        //获得token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //利用令牌进行登录
        try {
            //登录成功，返回首页
            subject.login(token);

            //设置session，判断是否显示登录按钮
            session.setAttribute("loginUser", username);

            return "redirect:/admin/blogs";
            //登陆失败，返回登录页
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "用户名不正确");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg", "账号被锁定");
            return "login";
        } catch (Exception e) {
            model.addAttribute("msg", "没有权限");
            return "login";
        }


    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "index";
    }

}
