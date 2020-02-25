package com.example.demo.controller.admincontroller;

import com.example.demo.pojo.Tag;
import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
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


/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 7,
                        sort = {"id"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("recommendBlog", blogService.getTop(4));
        model.addAttribute("page", tagService.list(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/add")
    public String toAddTag(){

        return "admin/tags_input";
    }

    @PostMapping("/tags/saveTag")
    public String addTag(Tag tag, RedirectAttributes model){

        if(tagService.getByName(tag.getName())!=null){
            model.addFlashAttribute("msg", "已经存在该标签");
        }else if("".equals(tag.getName())){
            model.addFlashAttribute("msg", "标签不能为空");
        }else{
            tagService.save(tag);
            model.addFlashAttribute("msg", "新增标签成功");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/update")
    public String toUpdateTag(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags_input";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@PathVariable Long id, Tag tag, RedirectAttributes model){
        if(tagService.getByName(tag.getName())!=null){
            model.addFlashAttribute("msg", "已经存在该标签");
        }else if("".equals(tag.getName())){
            model.addFlashAttribute("msg", "标签不能为空");
        }else{
            tagService.save(tag);
            model.addFlashAttribute("msg", "修改标签成功");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes model){
        tagService.deleteTag(id);
        model.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/tags";
    }


}
