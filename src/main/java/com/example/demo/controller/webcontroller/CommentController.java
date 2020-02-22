package com.example.demo.controller.webcontroller;

import com.example.demo.pojo.Comment;
import com.example.demo.service.BlogService;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/22 18:19
 * @Version: 1.0
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.headPicture}")
    private String headPicture;

    @GetMapping("/comment/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        model.addAttribute("comment", commentService.getComments(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comment")
    public String postComment(Comment comment){
        Long blogId = comment.getBlog().getId();
        //根据blogId保存Blog对象，建立关系
        comment.setBlog(blogService.getBlog(blogId));
        comment.setHeadPicture(headPicture);
        commentService.save(comment);
        return "redirect:/comment/"+blogId;
    }

}
