package com.example.demo.dao;

import com.example.demo.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type getByName(String name);

}
