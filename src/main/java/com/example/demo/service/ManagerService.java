package com.example.demo.service;

import com.example.demo.pojo.Manager;
import org.springframework.stereotype.Service;


public interface ManagerService {

    public Manager getManager(String username);


}
