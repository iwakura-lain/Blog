package com.example.demo.service;

import com.example.demo.NotFoundExc;
import com.example.demo.dao.TypeRepository;
import com.example.demo.pojo.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    //放入事务中
    @Transactional
    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.getOne(id);
        if(type1==null){
            throw new NotFoundExc("没有找到该分类");
        }
        //工具类方法，将type的所有属性赋值给type1
        BeanUtils.copyProperties(type, type1);
        return typeRepository.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
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
}
