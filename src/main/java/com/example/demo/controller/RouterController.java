package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping({"/", "/index", "/index.html"})
    public String index(){
        return "/index";
    }

    @RequestMapping("/adminInput")
    public String adminInput(){
        return "admin/blogs-input";
    }

    @RequestMapping("admin/blogs")
    public String blog(){
        return "admin/blogs";
    }

    @RequestMapping("admin/blogs-input")
    public String blogInput(){
        return "admin/blogs-input";
    }

    @RequestMapping({"/login", "/login.html"})
    public String login(){
        return "login";
    }

}
