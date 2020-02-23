package com.example.demo.service;

import com.example.demo.NotFoundException;
import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.TypeRepository;
import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private BlogRepository blogRepository;
    /**
     * 放入事务中
     * @return : 保存分类
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.getOne(id);
        if(type1==null){
            throw new NotFoundException("没有找到该分类");
        }
        //工具类方法，将type的所有属性赋值给type1
        BeanUtils.copyProperties(type, type1);
        return typeRepository.save(type1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteType(Long id) {
        //分类不能解除级联关系，因为分类必须存在
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.getByName(name);
    }

    @Override
    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> getTop(Integer size) {
        Sort orders = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, orders);

        return typeRepository.findTop(pageable);
    }
}
