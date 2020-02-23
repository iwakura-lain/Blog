package com.example.demo.controller.admincontroller;

import com.example.demo.dao.CommentRepository;
import com.example.demo.pojo.Comment;
import com.example.demo.service.CommentService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/23 17:50
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/admin")
public class CommController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment")
    public String toDelete(@PageableDefault(size = 7,
                         sort = {"creatTime"},
                         direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", commentService.getComments(pageable));
        return "/admin/comments";
    }

    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        commentService.deleteComment(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/comment";
    }

}
