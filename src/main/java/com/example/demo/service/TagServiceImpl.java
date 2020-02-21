package com.example.demo.service;

import com.example.demo.NotFoundException;
import com.example.demo.NotFoundException;
import com.example.demo.dao.TagRepository;
import com.example.demo.pojo.Tag;
import com.example.demo.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service

/**
 * @Author: antigenMHC
 * @Date: 2020/2/21 0:19
 * @Version: 1.0
 **/
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagRepository.getOne(id);

        //将tag的所有属性赋值给one
        BeanUtils.copyProperties(tag, one);

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

            String[] idArray = ids.split(",");
            for (String s : idArray) {
                id.add(Long.valueOf(s));
            }
        }
        return id;
    }

    @Override
    public List<Tag> getTagsById(String ids) {
        List<Long> id = getId(ids);

        return tagRepository.findAllById(id);
    }

    @Override
    public int getIdByName(String name) {
        return tagRepository.getIdByName(name);
    }

    @Override
    public List<Tag> getTop(Integer size) {
        Sort orders = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, orders);

        return tagRepository.findTop(pageable);
    }
}
