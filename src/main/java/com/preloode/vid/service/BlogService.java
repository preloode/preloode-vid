package com.preloode.vid.service;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Pagination;
import com.preloode.vid.repository.BlogCommentRepository;
import com.preloode.vid.repository.BlogRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class BlogService {


    @Autowired
    private Pagination pagination;

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MemberLogService memberLogService;


    public Map<String, Object> comment(HttpServletRequest request, Map<String, Object> preloodeAccount, Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Comment failed sent");
                put("result", false);
            }

        };

        Document insertOneData = data;
        Document insertOneAdministrator = new Document("id", preloodeAccount.get("_id"))
                .append("username", preloodeAccount.get("username"));
        String administratorInsertId = this.blogCommentRepository.insertOne(request, insertOneData, insertOneAdministrator);

        Document insertLogTarget = new Document("target", new Document("id", administratorInsertId)
                .append("name", data.get("username")));
        this.memberLogService.insert(request, insertOneAdministrator, "", "Add administrator", insertLogTarget);

        result.put("response", "Comment successfully sent");
        result.put("result", true);

        return result;

    }


    public Map<String, Object> dislike(HttpServletRequest request, Map<String, Object> preloodeAccount, Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Dislike failed");
                put("result", false);
            }

        };

        Document findEq = new Document("_id", data.get("id"));
        MongoCursor<Document> blogIterator = this.blogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (blogIterator.hasNext()) {

            Map<String, Object> blogMap = blogIterator.next();

            Map<String, Object> blogDislikeMap = (Map<String, Object>) blogMap.get("dislike");

            blogDislikeMap.put("amount", new BigDecimal(blogDislikeMap.get("amount").toString()).add(BigDecimal.ONE));

            Map<String, Object> blogDislikeContributorMap = (Map<String, Object>) blogDislikeMap.get("contributor");

            blogDislikeContributorMap.put("amount", new BigDecimal(blogDislikeContributorMap.get("amount").toString()).add(BigDecimal.ONE));

            ArrayList<String> blogDislikeContributorIdList = (ArrayList<String>) blogDislikeContributorMap.get("id");
            blogDislikeContributorIdList.add(preloodeAccount.get("_id").toString());
            blogDislikeContributorMap.put("id", blogDislikeContributorIdList);

            blogDislikeMap.put("contributor", blogDislikeContributorMap);

            Document updateOneEq = new Document("_id", blogMap.get("_id"));
            Document updateOneData = new Document("dislike", blogDislikeMap);
            Document updateOneAdministrator = new Document("id", "0")
                    .append("username", "System");
            this.blogRepository.updateOne(request, updateOneEq, updateOneData, updateOneAdministrator);

        }

        Document insertOneData = data;
        Document insertOneAdministrator = new Document("id", preloodeAccount.get("_id"))
                .append("username", preloodeAccount.get("username"));
        String administratorInsertId = this.blogCommentRepository.insertOne(request, insertOneData, insertOneAdministrator);

        Document insertLogTarget = new Document("target", new Document("id", administratorInsertId)
                .append("name", data.get("username")));
        this.memberLogService.insert(request, insertOneAdministrator, "", "Add administrator", insertLogTarget);

        result.put("response", "Comment successfully sent");
        result.put("result", true);

        return result;

    }


    public Map<String, Object> initializeData(Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Data failed initialized");
                put("result", false);
            }

        };

        Document findEq = new Document("_id", data.get("id"));
        MongoCursor<Document> blogIterator = this.blogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (blogIterator.hasNext()) {

            result.put("data", blogIterator.next());

        }

        result.put("result", true);

        return result;

    }


    public Map<String, Object> loadBlogEntry(String blogPath) {

        Map<String, Object> result = new HashMap<String, Object>();

        blogPath = blogPath.replaceAll("[^A-Za-z0-9/-]", "");
        Document findEq = new Document("path", blogPath);
        MongoCursor<Document> blogIterator = this.blogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (blogIterator.hasNext()) {

            result = blogIterator.next();

        }

        return result;

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


    public MongoCursor<Document> loadComment(HttpServletRequest request, String id) {

        return this.blogCommentRepository.findSort(new Document("created.timestamp", -1));

    }


    public Map<String, Object> updateView(HttpServletRequest request, Client client, String blogPath) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "View failed updated");
                put("result", false);
            }

        };

        blogPath = blogPath.replaceAll("[^A-Za-z0-9/-]", "");
        Document findEq = new Document("path", blogPath);
        MongoCursor<Document> blogIterator = this.blogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (blogIterator.hasNext()) {

            Map<String, Object> blogMap = blogIterator.next();

            Map<String, Object> blogViewMap = (Map<String, Object>) blogMap.get("view");

            ArrayList<String> blogViewIdList = (ArrayList<String>) blogViewMap.get("id");

            if (!blogViewIdList.contains(client.getId())) {

                blogViewIdList.add(client.getId());
                blogViewMap.put("id", blogViewIdList);
                blogViewMap.put("unique", new BigDecimal(blogViewMap.get("unique").toString()).add(BigDecimal.ONE));

            }

            ArrayList<String> blogViewSessionIdList = (ArrayList<String>) blogViewMap.get("session_id");

            if (!blogViewSessionIdList.contains(client.getJSessionId())) {

                blogViewSessionIdList.add(client.getJSessionId());
                blogViewMap.put("session_id", blogViewSessionIdList);
                blogViewMap.put("raw", new BigDecimal(blogViewMap.get("raw").toString()).add(BigDecimal.ONE));

            }

            Document updateOneEq = new Document("_id", blogMap.get("_id"));
            Document updateOneData = new Document("view", blogViewMap);
            Document updateOneAdministrator = new Document("id", "0")
                    .append("username", "System");
            this.blogRepository.updateOne(request, updateOneEq, updateOneData, updateOneAdministrator);

        }

        return result;

    }


}
