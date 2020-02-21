package com.example.demo.dao;


import com.example.demo.pojo.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findByUsername(String username);
    Manager findByUsernameAndPassword(String username, String password);


}
