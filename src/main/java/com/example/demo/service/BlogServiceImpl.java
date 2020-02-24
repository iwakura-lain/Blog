package com.example.demo.service;

import com.example.demo.NotFoundException;
import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.otherobj.BlogQuery;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Type;
import com.example.demo.utils.MarkdownUtils;
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

import javax.persistence.criteria.*;
import java.util.*;

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
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TagRepository tagRepository;

    @Override
    public Blog getBlog(Long id) {
        //对blog的文章部分进行处理，将md语法转为html
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            /**
             * 处理动态组合查询分页
             * criteriaQuery：条件容器
             * criteriaBuilder：模糊查询
             * */
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(!("".equals(blog.getTitle())) && blog.getTitle() != null){
                    //模糊查询,泛型指定通过属性"title"获得的数据类型是什么
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
        if("".equals(blog.getFlag())){
            blog.setFlag("原创");
        }
        if(blog.getId()==null){
            //初始化blog部分属性
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            //浏览次数
            blog.setViews(0);
            //作者
            blog.setManager(managerRepository.getOne(3L));
            //发布/保存
            blog.setPublish(blog.isPublish());
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog getBlogHtml(Long id) {
        Blog one = blogRepository.getOne(id);
        if(one==null){
            throw new NotFoundException("没有找到该博客");
        }
        //因为直接操作数据库中查询出来的对象可能会改变数据库，因此新建一个对象b
        Blog b = new Blog();
        //将数据库中查询出来的对象的所有属性临时赋给b，只用于前端显示
        BeanUtils.copyProperties(one, b);
        String content = b.getContent();
        //将markdown语法的字符串转为html字符串,并设置到blog对象中
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新浏览人数
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(blog.getId());
        if("".equals(blog.getTitle())){
            throw new NotFoundException("标题不能为空");
        }
        //设置新的状态
        one.setPublish(blog.isPublish());
        //过滤掉属性值为空的属性，不用其赋值
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlog(Long id) {
        List<Comment> list = blogRepository.getOne(id).getComments();
        if(list!=null){
            //解除级联关系
            for (Comment comment : list) {
                commentRepository.delete(comment);
            }
        }

        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlog(String name) {
        return blogRepository.getBlogByTitle(name);
    }

    @Override
    public Page<Blog> listAll(Pageable pageable) {
        return blogRepository.findBlogByPublishTrue(pageable);
    }

    @Override
    public List<Blog> getTop(Integer size) {
        Sort orders = Sort.by(Sort.Direction.DESC, "updateTime");
        //只需要查询第一页的内容，因此传0
        PageRequest request = PageRequest.of(0, size, orders);
        return blogRepository.findTop(request);
    }

    /**
     *自定义query，模糊查询内容和标题
     * */
    @Override
    public Page<Blog> searchBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> typeToBlog(Pageable pageable, Long id) {
        return blogRepository.findByTypeIdAndPublishTrue(pageable, id);
    }

    @Override
    public Page<Blog> tagToBlog(Pageable pageable, Long id) {
        //关联查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), id);
            }
        }, pageable);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Map<String, List<Blog>> archivesMap() {
        //获取所有年份
        List<String> time = blogRepository.findGroupByYear();
        Map<String, List<Blog>> map = new HashMap<>();
        //关联年份和对应年份下的blog
        for (String s : time) {
            map.put(s, blogRepository.findByYear(s));
        }
        return map;
    }
}
