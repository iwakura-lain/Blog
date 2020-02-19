package com.example.demo.service;

import com.example.demo.dao.TagRepository;
import com.example.demo.pojo.Tag;
import com.example.demo.pojo.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagRepository.getOne(id);
        if(one==null){
            try {
                throw new NotFoundException("不存在该标签");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        //将tag的所有属性赋值给one
        BeanUtils.copyProperties(one, tag);

        return tagRepository.save(one);
    }

    @Override
    public Page<Tag> list(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getByName(name);
    }
}
