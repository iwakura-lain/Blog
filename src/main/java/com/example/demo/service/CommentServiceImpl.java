package com.example.demo.service;

import com.example.demo.dao.CommentRepository;
import com.example.demo.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/22 18:30
 * @Version: 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Long blogId) {
        Sort orders = Sort.by(Sort.Direction.DESC, "creatTime");
        return commentRepository.getByBlogId(blogId, orders);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment save(Comment comment) {
        Long commentParentId = comment.getParentComment().getId();
        //如果不是默认值-1，说明是回复，不是发表评论，因此设置父comment
        if(commentParentId != -1){
            comment.setParentComment(commentRepository.getOne(commentParentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }
}
