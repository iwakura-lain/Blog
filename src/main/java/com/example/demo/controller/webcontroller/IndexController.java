package com.example.demo.controller.webcontroller;

import com.example.demo.otherobj.BlogQuery;
import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
import com.example.demo.service.TypeService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 14:42
 * @Version: 1.0
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @RequestMapping({"/", "/index", "/index.html"})
    public String index(@PageableDefault(size = 7,
                        sort = {"creatTime"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        model.addAttribute("types", typeService.getTop(6));
        model.addAttribute("tags", tagService.getTop(9));
        model.addAttribute("recommendBlog", blogService.getTop(4));
        model.addAttribute("page", blogService.listAll(pageable));
        return "/index";
    }

    @PostMapping("/search/")
    public String search(@PageableDefault(size = 7,
                         sort = {"creatTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,
                         @RequestParam String query){
        //"%"+query+"%"模糊查询
        model.addAttribute("page", blogService.searchBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search_result";
    }


}
