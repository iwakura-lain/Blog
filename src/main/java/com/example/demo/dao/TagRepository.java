package com.example.demo.dao;

import com.example.demo.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag getByName(String name);

}
