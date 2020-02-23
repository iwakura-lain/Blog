package com.example.demo.controller.admincontroller;

import com.example.demo.otherobj.BlogQuery;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Manager;
import com.example.demo.pojo.Tag;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admin")

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class BlogsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String toBlogs(@PageableDefault(size = 7,
                          sort = {"creatTime"},
                          direction = Sort.Direction.DESC) Pageable pageable,
                          BlogQuery blog,
                          Model model){
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        model.addAttribute("recommendBlog", blogService.getTop(4));

        return "/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,
                         sort = {"creatTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model){

        model.addAttribute("page", blogService.listBlog(pageable,blog));
        //刷新片段
        return "/admin/blogs :: blogList";
    }

    @GetMapping("/blogs/add")
    public String toBlogAdd(Model model){
        getTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "/admin/blogs_input";
    }

    @GetMapping("/blogs/{id}/update")
    public String toBlogUpdate( @PathVariable Long id, Model model, Blog b){
        getTypeAndTag(model);

        Blog blog = blogService.getBlog(id);
        blog.initTags();
        model.addAttribute("blog", blog);
        model.addAttribute("isUpdate", 1);
        return "/admin/blogs_input";
    }

    private void getTypeAndTag(Model model){
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("tags", tagService.getAll());
    }


    @PostMapping("/blogs/add")
    public String blogAdd(Blog blog, HttpSession session, RedirectAttributes attributes, Model model){
        //获取当前登录用户
        blog.setManager((Manager) session.getAttribute("manager"));

        model.addAttribute("types", typeService.getAll());
        model.addAttribute("tags", tagService.getAll());

        if(blog.getType().getId() == null | "".equals(blog.getContent()) | "".equals(blog.getDescription())){
            model.addAttribute("msg", "分类未选择/博客内容/博客描述未填写");
            return "/admin/blogs_input";
        }

        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getTagsById(blog.getTagIds()));

        Blog saveBlog;

        if(blog.getId()==null){
            saveBlog = blogService.save(blog);
        }else {
            saveBlog = blogService.updateBlog(blog.getId(), blog);
        }

        if(saveBlog==null){
            attributes.addFlashAttribute("msg", "新增失败");
        } else {
            attributes.addFlashAttribute("msg", "新增成功");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }

}
