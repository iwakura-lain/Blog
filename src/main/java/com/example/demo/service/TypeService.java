package com.example.demo.service;


import com.example.demo.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeService {

    Type save(Type type);
    Type getType(Long id);

    /**
     * 分页查询
     * */
    Page<Type> listType(Pageable pageable);


    Type updateType(Long id, Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> getAll();

    /**
     * 获取用于前端展示的type列表
     * @param size : 显示长度
     * */
    List<Type> getTop(Integer size);
}
