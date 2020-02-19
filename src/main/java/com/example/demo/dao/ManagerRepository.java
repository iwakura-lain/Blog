package com.example.demo.dao;


import com.example.demo.pojo.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findByUsername(String username);
    Manager findByUsernameAndPassword(String username, String password);


}
