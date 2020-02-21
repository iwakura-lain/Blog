package com.example.demo.service;

import com.example.demo.NotFoundException;
import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.otherobj.BlogQuery;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Type;
import com.example.demo.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    ManagerRepository managerRepository;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog save(Blog blog) {
        //如果Id等于null说明是新增，在这时初始化创建时间和浏览次数
        if(blog.getId()==null){
            //初始化blog部分属性
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            //浏览次数
            blog.setViews(0);
            //作者
            blog.setManager(managerRepository.findByUsername("antigenMHC"));
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(blog.getId());
        if("".equals(blog.getTitle())){
            throw new NotFoundException("标题不能为空");
        }
        //过滤掉属性值为空的属性，不用其赋值
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlog(String name) {
        return blogRepository.getBlogByTitle(name);
    }

    @Override
    public Page<Blog> listAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> getTop(Integer size) {
        Sort orders = Sort.by(Sort.Direction.DESC, "updateTime");
        //只需要查询第一页的内容，因此传0
        PageRequest request = PageRequest.of(0, size, orders);
        return blogRepository.findTop(request);
    }

    @Override
    public Page<Blog> searchBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }
}
