package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //一对多
    //代表被Blog中的type维护关系, mappedBy用于被维护关系的一端
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

}
