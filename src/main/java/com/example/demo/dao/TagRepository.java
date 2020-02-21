package com.example.demo.dao;

import com.example.demo.pojo.Tag;
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
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag getByName(String name);
    int getIdByName(String name);

    /**
     * 通过分页查询拥有blog数目最多的tag
     * @param pageable : 分页
     * */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
