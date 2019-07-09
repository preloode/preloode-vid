package com.preloode.vid.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Account;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Path;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.service.IndexService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping(value = "")
public class IndexController {


    @Autowired
    private Path path;

    @Autowired
    private Url url;

    @Autowired
    private Client client;

    @Autowired
    private Account account;

    @Autowired
    private Head head;

    @Autowired
    private IndexService indexService;

    private FindIterable<Document> blogCategoryIterable;

    private FindIterable<Document> blogIterable;

    private Map<String, Object> preloodeAccount;


    @ModelAttribute("path")
    public Path path() {

        return this.path;

    }


    @ModelAttribute("preloodeAccount")
    public Map<String, Object> preloodeAccount(HttpServletRequest request, HttpServletResponse response) {

        this.client.checkAccess(request, response);

        this.preloodeAccount = this.account.initializeAccount(request);

        return this.preloodeAccount;

    }


    @ModelAttribute("setting")
    public Setting setting(HttpServletRequest request) {

        return this.head.initialize("Index", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> data) {

        this.blogCategoryIterable = this.indexService.loadBlogCategoryIterable();
        MongoCursor<Document> blogCategoryMenuIterator = this.blogCategoryIterable.sort(new Document("name", 1)).iterator();
        MongoCursor<Document> blogCategoryLatestIterator = this.blogCategoryIterable.sort(new Document("created.timestamp", -1)).skip(0).limit(10).iterator();

        this.blogIterable = this.indexService.loadBlogIterable();
        MongoCursor<Document> blogFeatureBigIterator = this.blogIterable.sort(new Document("rate.amount", -1)).skip(0).limit(1).iterator();
        MongoCursor<Document> blogFeatureSmallIterator = this.blogIterable.sort(new Document("rate.amount", -1)).skip(1).limit(4).iterator();
        MongoCursor<Document> blogLatestIterator = this.blogIterable.sort(new Document("created.timestamp", -1)).skip(0).limit(10).iterator();
        MongoCursor<Document> blogTopRatedIterator = this.blogIterable.sort(new Document("rate.amount", -1)).skip(0).limit(10).iterator();
        MongoCursor<Document> blogTopViewedIterator = this.blogIterable.sort(new Document("view.raw", -1)).skip(0).limit(10).iterator();

        data.put("blogCategoryLatest", blogCategoryLatestIterator);
        data.put("blogCategoryMenu", blogCategoryMenuIterator);
        data.put("blogFeatureBig", blogFeatureBigIterator);
        data.put("blogFeatureSmall", blogFeatureSmallIterator);
        data.put("blogLatest", blogLatestIterator);
        data.put("blogTopRated", blogTopRatedIterator);
        data.put("blogTopViewed", blogTopViewedIterator);

        return "index";

    }


}
