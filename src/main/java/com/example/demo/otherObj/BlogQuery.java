package com.example.demo.otherObj;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 封装查询条件
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

}
