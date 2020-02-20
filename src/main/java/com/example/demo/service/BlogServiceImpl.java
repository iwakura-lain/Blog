package com.example.demo.service;

import com.example.demo.NotFoundExc;
import com.example.demo.dao.BlogRepository;
import com.example.demo.otherObj.BlogQuery;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Type;
import javassist.NotFoundException;
import org.apache.ibatis.javassist.bytecode.SignatureAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            /**
             * 处理动态组合查询
             * criteriaQuery：条件容器
             * criteriaBuilder：模糊查询
             * */
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(!("".equals(blog.getTitle())) && blog.getTitle() != null){
                    //like查询条件,<String>指定通过"title"获得的数据类型是什么
                    list.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    list.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    list.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //where查询
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        }, pageable);

    }

    @Transactional
    @Override
    public Blog save(Blog blog) {
        //初始化blog部分属性
        blog.setCreatTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);  //浏览次数
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(blog.getId());
        if(blog.getTitle().equals("")){
            throw new NotFoundExc("标题不能为空");
        }
        BeanUtils.copyProperties(one, blog);
        return one;
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlog(String name) {
        return blogRepository.getBlogByTitle(name);
    }
}
