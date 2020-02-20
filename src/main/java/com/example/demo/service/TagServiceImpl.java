package com.example.demo.service;

import com.example.demo.NotFoundExc;
import com.example.demo.dao.TagRepository;
import com.example.demo.pojo.Tag;
import com.example.demo.pojo.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagRepository.getOne(id);
        if(one==null){
            throw new NotFoundExc("没有找到该标签");
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

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    private List<Long> getId(String ids){
        List<Long> id = new ArrayList<>();
        if(ids!=null & !("".equals(ids))){
            String[] idArraty = ids.split(",");

            for (int i = 0; i < idArraty.length; i++) {

                id.add(Long.valueOf(idArraty[i]));
            }
        }

        return id;
    }

    @Override
    public List<Tag> getTagsById(String ids) {
        List<Long> id = getId(ids);

        return tagRepository.findAllById(id);
    }
}
