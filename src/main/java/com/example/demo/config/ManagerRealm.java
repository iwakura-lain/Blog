package com.example.demo.config;

import com.example.demo.pojo.Manager;
import com.example.demo.service.ManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现shiro-Realm的认证
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
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
        //获取数据库中数据
        Manager manager = managerService.getManager(token.getUsername());

        if(manager==null){
            return null;
        }
        //使用ByteSource.Util.bytes计算盐值
        ByteSource salt = ByteSource.Util.bytes(manager.getUsername());

        return new SimpleAuthenticationInfo(manager ,manager.getPassword(), salt, getName());

    }
}
