package com.example.demo.otherobj;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * 封装联合查询条件，作为一个单独对象
 *@Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 * */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

}
