package com.example.demo.service;

import com.example.demo.dao.CommentRepository;
import com.example.demo.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
    /**
     * 存放递归迭代出来的所有评论
     * */
    private List<Comment> replays = new ArrayList<>();

    /**
     * 对评论进行分层加工并返回
     * 我们需要先拿到第一层的数据，即parentId为null的
     * */
    @Override
    public List<Comment> getComments(Long blogId) {
        Sort orders = Sort.by("creatTime");
        //获取所有的顶级评论，对顶级评论进行迭代处理
        List<Comment> comments1 = commentRepository.getByBlogIdAndParentCommentIsNull(blogId, orders);

        return eachComment(comments1);
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

    @Override
    public Page<Comment> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.getOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteComment(Long id) {
        Comment one = commentRepository.getOne(id);
        if(one!=null){
            if(one.getBlog()!=null){
                one.setBlog(null);
            }
            if( one.getReplayComments()!=null){
                deleteReplay(one);
            }
            if(one.getParentComment()!=null){
                one.setParentComment(null);
            }
        }
        commentRepository.delete(one);
    }
    /**
     * 递归删除子评论及置空父评论
     * */
    private void deleteReplay(Comment comment){
        List<Comment> comments = comment.getReplayComments();

        comment.setParentComment(null);
        comment.setReplayComments(null);
        commentRepository.delete(comment);

        for (Comment comment1 : comments) {
            if(comment1.getReplayComments()!=null){
                deleteReplay(comment1);
            }
        }
    }

    /**
     * 循环每个顶级的评论根节点
     * */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> list = new ArrayList<>();
        //迭代复制根评论的源数据，避免对原数据进行修改
        for (Comment comment : comments) {
            Comment tmp = new Comment();
            BeanUtils.copyProperties(comment, tmp);
            list.add(tmp);
        }
        //合并评论的各层级子代到为第一级子代中
        combineChildren(list);
        return list;
    }
    /**
     * 获取顶层对象的所有子评论
     * */
    private void combineChildren(List<Comment> comments){

        for (Comment comment : comments) {
            List<Comment> tmp = comment.getReplayComments();
            //循环递归出当前父评论的所有子评论，并存放到集合中
            for (Comment replay : tmp) {
                getReplays(replay);
            }
            //设置当前父评论的子评论
            comment.setReplayComments(replays);
            //清空集合，继续找到父评论的子评论
            replays = new ArrayList<>();
        }
    }

    /**
     * 存放所有迭代出的评论进入集合
     * */
    private void getReplays(Comment comment){
        //添加根节点
        replays.add(comment);
        //如果有子节点
        if(comment.getReplayComments().size() > 0){
            //递归获得子节点
            List<Comment> list = comment.getReplayComments();
            for (Comment comment1 : list) {
                getReplays(comment1);
            }
        }
    }
}
