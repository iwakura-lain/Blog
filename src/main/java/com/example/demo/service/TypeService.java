package com.example.demo.service;


import com.example.demo.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
