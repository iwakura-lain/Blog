package com.example.demo.controller.webcontroller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Manager;
import com.example.demo.service.BlogService;
import com.example.demo.service.CommentService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    @Value("${manager.headPicture}")
    private String managerPicture;

    @GetMapping("/comment/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){

        model.addAttribute("comment", commentService.getComments(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comment")
    public String postComment(Comment comment, HttpSession session){

        Long blogId = comment.getBlog().getId();
        //设置属性，区分访客和master
        Manager manager = (Manager) session.getAttribute("manager");

        if(manager!=null){
            comment.setHeadPicture(managerPicture);
            comment.setMaster(true);
        }else{
            comment.setHeadPicture(headPicture);
        }

        //根据blogId保存Blog对象，建立关系
        comment.setBlog(blogService.getBlog(blogId));
        comment.setHeadPicture(headPicture);
        commentService.save(comment);
        return "redirect:/comment/"+blogId;
    }


}
