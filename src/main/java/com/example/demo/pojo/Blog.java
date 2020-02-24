package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Blog文章类
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {

    /**
     * 标识主键,默认自动生成
     * */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 文章标题
     * */
    private String title;

    /**
     * 文章内容
     * */
    private String content;

    /**
     * 文章首图
     * */
    private String firstPicture;

    /**
     * 标记
     * */

    private String flag;
    /**
     * 浏览次数
     * */

    private Integer views;
    /**
     * 赞赏开关
     * */
    private boolean appreciation;

    /**
     * 转载声明开关
     * */
    private boolean shareState;

    /**
     * 评论是否开启
     * */
    private boolean comment;

    /**
     * 发布/保存为草稿
     * */
    private boolean publish;

    /**
     * 是否推荐
     * */
    private boolean recommend;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 这个属性不需要保存到数据库，仅做为查询标签的条件
     * */
    @Transient
    private String tagIds;

    /**
     * 多对一，Blog对Type而言是多对一
     */
    @ManyToOne
    private Type type;

    /**
     * Blog对Tag是多对多，设置级联关系，当新增一个Blog的时候，连同新增一个新的标签
     * 同时把这个tag保存到数据库里面
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    /**
     * 多个Blog对应一个Manager
     * */
    @ManyToOne
    private Manager manager;

    /**
     * 博客描述
     * */
    private String description;
    /**
     * 多对一中，多的一方是关系的维护方
     * OneToMany代表当前实体类和被标注对象的对应关系是
     * 一个当前实体类对应多个被标注对象
     **/
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
    /**
     * 初始化标签字符串，controller层调用该方法后前端渲染
     * */
    public void initTags(){
        this.tagIds = parseTags(this.tags);
    }
    /**
     * 为了前端显示多标签，在这里将List转化为"1,2,3"形式的字符串
     * 这样前端就会自动显示多个对应ID的标签
     * @return：tagid组成的字符串/空list
     * */
    private String parseTags(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        else {
            return tagIds;
        }
    }

}
