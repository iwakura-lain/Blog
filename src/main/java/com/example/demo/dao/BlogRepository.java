package com.example.demo.dao;

import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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
        @Query("select b from Blog b where b.recommend = true")
        List<Blog> findTop(Pageable pageable);

        /**
         * ?1代表传递第一个参数到该位置
         * select * from Blog where title like "%内容%";
         * "%内容%"代表使用模糊查询，Query中使用?1不会帮我们加上%，因此需要在controller中手动加上
         * */
        @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
        Page<Blog> findByQuery(String query, Pageable pageable);
}
