package com.example.demo.service;

import com.example.demo.pojo.Comment;

import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/22 18:30
 * @Version: 1.0
 **/
public interface CommentService {

    List<Comment> getComments(Long blogId);

    Comment save(Comment comment);

}
