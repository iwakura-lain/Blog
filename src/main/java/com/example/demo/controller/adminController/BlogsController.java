package com.example.demo.controller.adminController;

import com.example.demo.otherObj.BlogQuery;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Manager;
import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
import com.example.demo.service.TypeService;
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

@Controller
@RequestMapping("/admin")
public class BlogsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String toBlogs(@PageableDefault(size = 2,
                          sort = {"updateTime"},
                          direction = Sort.Direction.DESC) Pageable pageable,
                          BlogQuery blog,
                          Model model){
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("page", blogService.listBlog(pageable,blog));

        return "/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,
                         sort = {"updateTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model){

        model.addAttribute("page", blogService.listBlog(pageable,blog));
        //刷新片段
        return "/admin/blogs :: blogList";
    }

    @GetMapping("/blogs/add")
    public String toBlogAdd(Model model){
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("tags", tagService.getAll());

        return "/admin/blogs_input";
    }


    @PostMapping("/blogs/add")
    public String blogAdd(Blog blog, HttpSession session, RedirectAttributes attributes, Model model){
        //获取当前登录用户
        blog.setManager((Manager) session.getAttribute("manager"));

        model.addAttribute("types", typeService.getAll());
        model.addAttribute("tags", tagService.getAll());

        if(blog.getType().getId() == null | blog.getContent().equals("")){
            model.addAttribute("msg", "分类未选择/博客内容未填写");
            return "/admin/blogs_input";
        }
        if(blogService.getBlog(blog.getTitle())!=null){
            model.addAttribute("msg", "已经有这篇博客了！gkd下一篇");
            return "/admin/blogs_input";
        }

        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getTagsById(blog.getTagIds()));
        Blog saveBlog = blogService.save(blog);
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
