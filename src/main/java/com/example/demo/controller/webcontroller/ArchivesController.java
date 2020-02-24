package com.example.demo.controller.webcontroller;

import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/24 13:21
 * @Version: 1.0
 **/
@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("archMap", blogService.archivesMap());
        model.addAttribute("count", blogService.countBlog());
        model.addAttribute("recommendBlog", blogService.getTop(4));

        return "/archives";
    }
}
