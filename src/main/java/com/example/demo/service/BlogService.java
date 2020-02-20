package com.example.demo.service;

import com.example.demo.otherObj.BlogQuery;
import com.example.demo.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
