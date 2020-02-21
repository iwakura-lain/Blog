package com.example.demo.service;

import com.example.demo.otherobj.BlogQuery;
import com.example.demo.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);
    /**
     * @Param blog: 查询条件封装为一个Blog对象
     * */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog save(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Blog getBlog(String name);

    Page<Blog> listAll(Pageable pageable);

    List<Blog> getTop(Integer size);

    Page<Blog> searchBlog(String query, Pageable pageable);
}
