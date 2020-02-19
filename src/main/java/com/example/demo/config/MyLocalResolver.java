package com.example.demo.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class MyLocalResolver implements LocaleResolver {
    @Override
    //解析请求头
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求中的参数l
        String language = httpServletRequest.getParameter("l");

        Locale locale = Locale.getDefault();  //如果没有参数l就使用默认的

        //如果携带国际化参数l，则使用该参数
        if(!StringUtils.isEmpty(language)){
            //zh_CN
            String[] s = language.split("_");
            //把国家,地区传过去，只要你在message.basename里面配置了，并且有对应的配置文件
            //就可以自动配置
            locale = new Locale(s[0], s[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
