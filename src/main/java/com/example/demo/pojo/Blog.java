package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {

    //标识主键
    @Id
    //默认自动生成
    @GeneratedValue
    private Long id;

    private String title;      //文章标题
    private String content;    //文章内容
    private String firstPicture;  //文章首图
    private String flag;     //标记
    private Integer views;  //浏览次数
    private boolean appreciation;  //赞赏开关
    private boolean shareState; //转载声明开关
    private boolean comment;   //评论是否开启
    private boolean publish;   //发布/保存为草稿
    private boolean recommend; //是否推荐

    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //这个属性不需要保存到数据库
    @Transient
    private String tagIds;

    //多对一
    //Blog对Type而言是多对一
    @ManyToOne
    private Type type;

    //Blog对Tag是多对多
    //设置级联关系，当新增一个Blog的时候，连同新增一个新的标签
    //同时把这个tag保存到数据库里面
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    //多个Blog对应一个Manager
    @ManyToOne
    private Manager manager;

    //多对一中，多的一方是关系的维护方
    //OneToMany代表当前实体类和被标注对象的对应关系是
    //一个当前实体类对应多个被标注对象
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

}
