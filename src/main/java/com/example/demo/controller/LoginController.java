package com.example.demo.controller;

import com.example.demo.service.BlogService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.TagService;
import com.example.demo.service.TypeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class LoginController {


    @Autowired
    private BlogService blogService;
    @Autowired
    private ManagerService manager;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @RequestMapping({"/login", "/login.html"})
    public String login(){
        return "/login";
    }

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
            session.setAttribute("email", manager.getManager(username).getEmail());
            session.setAttribute("nickName", manager.getManager(username).getNickname());

            session.setAttribute("manager", manager.getManager(username));

            return "redirect:/admin/blogs";
            //登陆失败，返回登录页
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "用户名不正确");
            session.removeAttribute("loginUser");
            return "/login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码错误");
            session.removeAttribute("loginUser");
            return "/login";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg", "账号被锁定");
            session.removeAttribute("loginUser");
            return "/login";
        } catch (Exception e) {
            model.addAttribute("msg", "没有权限");
            session.removeAttribute("loginUser");
            return "/login";
        }

    }

    @RequestMapping("/logOut")
    public String logOut(@PageableDefault(size = 7,
                         sort = {"creatTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, HttpSession session){

        model.addAttribute("types", typeService.getTop(6));
        model.addAttribute("tags", tagService.getTop(9));
        model.addAttribute("recommendBlog", blogService.getTop(4));
        model.addAttribute("page", blogService.listAll(pageable));
        session.removeAttribute("loginUser");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "index";
    }

}
