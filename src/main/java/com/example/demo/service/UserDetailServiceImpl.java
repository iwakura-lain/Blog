//package com.example.demo.service;
//
//import com.example.demo.pojo.Manager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Component
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    ManagerServiceImpl managerService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Manager manager = null;
//        if(StringUtils.isEmpty(username)){
//            throw new UsernameNotFoundException("没有收到用户账号");
//        }else{
//            manager = managerService.getManager(username);
//            if(manager==null){
//                throw new UsernameNotFoundException("用户"+username+"不存在");
//            }
//        }
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "admin";
//            }
//        });
//        /**
//         * 创建一个用于认证的用户对象并返回，包括：用户名，密码，角色
//         */
//        return new User(manager.getUsername(), manager.getPassword(),grantedAuthorities);
//
//    }
//}
