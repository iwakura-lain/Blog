package com.example.demo.dao;

import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

        Blog getBlogByTitle(String title);

        /**
         * 通过分页查询显示推荐的blog
         * @param pageable : 分页
         * */
        @Query("select b from Blog b where b.recommend = true AND b.publish=true")
        List<Blog> findTop(Pageable pageable);

        /**
         * ?1代表传递第一个参数到该位置
         * select * from Blog where title like "%内容%";
         * "%内容%"代表使用模糊查询，Query中使用?1不会帮我们加上%，因此需要在controller中手动加上
         * */
        @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
        Page<Blog> findByQuery(String query, Pageable pageable);

        @Transactional(rollbackFor = Exception.class)
        @Modifying
        @Query("update Blog b set  b.views = b.views+1 where b.id = ?1")
        int updateViews(Long id);

        Page<Blog> findByTypeIdAndPublishTrue(Pageable pageable, Long id);

        /**
         * 查询日期列表,获得所有年份
         * function('date_format', b.updateTime, '%Y') 表示查询方法是使用日期格式化获得b.updateTime中的年份Y
         *
         **/
        @Query("select function('date_format', b.updateTime, '%Y')  as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year desc ")
        List<String> findGroupByYear();

        /**
         * 获得对应年份下的所有博客
         * */
        @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1 and b.publish=true")
        List<Blog> findByYear(String year);

        Page<Blog> findBlogByPublishTrue(Pageable pageable);

}
