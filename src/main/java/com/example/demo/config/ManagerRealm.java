package com.example.demo.config;

import com.example.demo.pojo.Manager;
import com.example.demo.service.ManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerRealm extends AuthorizingRealm {

    @Autowired
    ManagerService managerService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        Manager manager = managerService.getManager(token.getUsername());

        if(manager==null){
            return null;
        }

        //使用ByteSource.Util.bytes计算盐值
        ByteSource bytes = ByteSource.Util.bytes(manager.getUsername() + manager.getBlogs() + manager.getType());
        System.out.println(bytes);
        return new SimpleAuthenticationInfo(manager ,manager.getPassword(), bytes, manager.getUsername());

    }
}
