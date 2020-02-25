package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
/**
 * 配置德鲁伊
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //获取后台监控对象，并设置访问路径
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //利用Map来增加配置, 参数key是固定的
        HashMap<String, String> init = new HashMap<>();

        init.put("loginUsername", "admin");
        init.put("loginPassword", "Aa1404006199");

        //允许谁可以访问,如果v为空，则全部都可以访问
        init.put("allow", "");

        //禁止访问, 禁止对应ip地址访问
        init.put("zhangshijie", "192.168.0.1");

        //设置初始化参数，账号密码等
        bean.setInitParameters(init);
        return bean;
    }

    //过滤器
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        //设置过滤器
        bean.setFilter(new WebStatFilter());

        //一样的，k是固定，想看有哪些k值，可以点进WebStatFilter看下
        Map<String, String> initParameters = new HashMap<>();

        //exclusions，过滤，不对v中的进行统计
        initParameters.put("exclusions", "");

        bean.setInitParameters(initParameters);
        return bean;
    }
}
