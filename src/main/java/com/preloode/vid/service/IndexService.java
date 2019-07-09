package com.preloode.vid.service;

import com.mongodb.client.FindIterable;
import com.preloode.vid.repository.BlogCategoryRepository;
import com.preloode.vid.repository.BlogRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IndexService {


    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;


    public FindIterable<Document> loadBlogIterable() {

        return this.blogRepository.find();

    }


    public FindIterable<Document> loadBlogCategoryIterable() {

        return this.blogCategoryRepository.find();

    }


}
