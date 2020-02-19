package com.example.demo.service;

import com.example.demo.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Tag save(Tag tag);

    Tag getTag(Long id);

    void deleteTag(Long id);

    Tag updateTag(Long id, Tag tag);

    Page<Tag> list(Pageable pageable);

    Tag getByName(String name);

}
