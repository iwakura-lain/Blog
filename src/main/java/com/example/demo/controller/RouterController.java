package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping({"/", "/index", "/index.html"})
    public String index(){
        return "/index";
    }

    @RequestMapping("/admin-input")
    public String adminInput(){
        return "/blogs_input";
    }

    @RequestMapping("admin/blogs")
    public String blog(){
        return "/admin/blogs";
    }

    @RequestMapping("admin/blogsInput")
    public String blogInput(){
        return "/admin/blogs_input";
    }

    @RequestMapping({"/login", "/login.html"})
    public String login(){
        return "/login";
    }


}
