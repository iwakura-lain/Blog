package com.example.demo.service;

import com.example.demo.dao.ManagerRepository;
import com.example.demo.pojo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerRepository managerRepository;


    @Override
    public Manager getManager(String username) {

        return managerRepository.findByUsername(username);
    }

}
