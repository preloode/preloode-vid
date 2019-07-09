package com.preloode.vid.service;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Pagination;
import com.preloode.vid.repository.BlogCategoryRepository;
import com.preloode.vid.repository.BlogRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Service
public class BlogCategoryService {


    @Autowired
    private Pagination pagination;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;


    public MongoCursor<Document> loadBlogCategoryPagination(HttpServletRequest request, Map<String, Object> preloodeAccount, String parentPath, Integer page) {

        Map<String, Object> paginationAccount = new HashMap<String, Object>();
        paginationAccount.put("id", preloodeAccount.get("_id"));
        paginationAccount.put("cookie", "preloode_pagination_blog_category");
        String size = this.pagination.load(request, paginationAccount);

        parentPath = parentPath.replaceAll("[^A-Za-z0-9/-]", "");
        Document filterDocument = new Document("parent.path", parentPath);

        return this.blogCategoryRepository.findPagination(filterDocument, new Document("created.timestamp", -1), (page - 1), Integer.parseInt(size));

    }


    public MongoCursor<Document> loadBlogPagination(HttpServletRequest request, Map<String, Object> preloodeAccount, String categoryPath, Integer page) {

        Map<String, Object> paginationAccount = new HashMap<String, Object>();
        paginationAccount.put("id", preloodeAccount.get("_id"));
        paginationAccount.put("cookie", "preloode_pagination_blog");
        String size = this.pagination.load(request, paginationAccount);

        categoryPath = categoryPath.replaceAll("[^A-Za-z0-9/-]", "");
        Document filterDocument = new Document("category.path", categoryPath);

        return this.blogRepository.findPagination(filterDocument, new Document("created.timestamp", -1), (page - 1), Integer.parseInt(size));

    }


}
