package com.example.demo.service;

import com.example.demo.otherobj.BlogQuery;
import com.example.demo.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);
    /**
     * @Param blog: 查询条件封装为一个Blog对象
     * */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog save(Blog blog);

    Blog getBlogHtml(Long id);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Blog getBlog(String name);

    Page<Blog> listAll(Pageable pageable);

    List<Blog> getTop(Integer size);

    Page<Blog> searchBlog(String query, Pageable pageable);

    Page<Blog> typeToBlog(Pageable pageable, Long id);

    Page<Blog> tagToBlog(Pageable pageable, Long id);
    /**
     * 返回blog总数目
     * @return ：blog总条数
     * */
    Long countBlog();

    /**
     * 归档，String：年份；List：对应年份的数据列表
     * @return : 年份和blog的对应map
     * */
    Map<String, List<Blog>> archivesMap();
}
