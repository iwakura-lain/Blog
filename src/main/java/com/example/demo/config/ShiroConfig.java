package com.example.demo.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.demo.pojo.Manager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class ShiroConfig {


    /**
     * ShiroFilterFactoryBean：绑定SecurityManager
     * */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager webSecurity){

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilter.setSecurityManager(webSecurity);

        /*
         *  anon: 无需认证就可访问
         *  authc：必须认证了，才可以访问
         *  user：必须拥有了‘记住我’功能才能用
         *  perms：拥有对某个资源的权限才能访问
         *  role：拥有某个角色权限才可以访问
         * */

        Map<String, String> definitionMap = new LinkedHashMap<>();

        //认证
        definitionMap.put("/admin/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(definitionMap);

        //设置登录请求,被拦截了就发送这个请求
        shiroFilter.setLoginUrl("/login");

        //设置未授权请求，如果未授权则发送该请求
        shiroFilter.setUnauthorizedUrl("/index");

        return shiroFilter;
    }

    /**
     * DefaultWebSecurityManager：绑定realm
     * */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("managerRealm") ManagerRealm managerRealm){

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联Realm
        defaultWebSecurityManager.setRealm(managerRealm);

        return defaultWebSecurityManager;
    }

    /**
     * 解密
     * */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1024);

        return hashedCredentialsMatcher;
    }

    /**
     * realm对象，自定义
     * */
    @Bean
    public ManagerRealm managerRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        ManagerRealm managerRealm = new ManagerRealm();
        managerRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return managerRealm;
    }


    /**
     * 整合shiroDialect 用于整合thymeleaf和shiro
     * */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
