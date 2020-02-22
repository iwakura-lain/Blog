package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/22 18:34
 * @Version: 1.0
 **/
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getByBlogId(Long blogId, Sort sort);


}
