package com.example.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 会判断所有标注有Controller这个注解的控制器，如果有异常则拦截下来
 * */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 获取日志(slf4j包下的)
     * */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 该注解标识该方法可以用作异常处理,拦截Exception级别的异常信息
     * */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest http, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        //记录日志
        //会将,后的参数传递到{}中，顺序传递
        logger.error("请求路径：{}, 异常信息：{}", http.getRequestURI(), e);

        modelAndView.addObject("url", http.getRequestURI());
        modelAndView.addObject("exception", e);
        //设置返回到哪个页面
        modelAndView.setViewName("error/error");

        return modelAndView;
    }

}
