package com.example.demo.controller.webcontroller;

import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/24 15:07
 * @Version: 1.0
 **/
@Controller
public class AboutController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/about/")
    public String about(Model model){

        model.addAttribute("recommendBlog", blogService.getTop(3));

        return "about";
    }
}
