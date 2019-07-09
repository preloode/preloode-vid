package com.preloode.vid.service;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Pagination;
import com.preloode.vid.repository.BlogStarRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Service
public class BlogStarService {


    @Autowired
    private Pagination pagination;

    @Autowired
    private BlogStarRepository blogStarRepository;


    public Map<String, Object> loadBlogStarEntry(String blogStarPath) {

        Map<String, Object> result = new HashMap<String, Object>();

        blogStarPath = blogStarPath.replaceAll("[^A-Za-z0-9/-]", "");
        Document findEq = new Document("path", blogStarPath);
        MongoCursor<Document> blogStarIterator = this.blogStarRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (blogStarIterator.hasNext()) {

            result = blogStarIterator.next();

        }

        return result;

    }


    public MongoCursor<Document> loadBlogStarPagination(HttpServletRequest request, Map<String, Object> preloodeAccount, Integer page) {

        Map<String, Object> paginationAccount = new HashMap<String, Object>();
        paginationAccount.put("id", preloodeAccount.get("_id"));
        paginationAccount.put("cookie", "preloode_pagination_blog_star");
        String size = this.pagination.load(request, paginationAccount);

        Document filterDocument = new Document();

        return this.blogStarRepository.findPagination(filterDocument, new Document("created.timestamp", -1), (page - 1), Integer.parseInt(size));

    }


}
