package com.example.demo.dao;

import com.example.demo.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type getByName(String name);

    /**
     * 通过分页查询拥有blog数目最多的type
     * @param pageable : 分页
     * */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
