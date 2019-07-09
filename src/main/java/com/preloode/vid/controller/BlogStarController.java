package com.preloode.vid.controller;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Account;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Path;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.service.BlogStarService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping(value = "/star")
public class BlogStarController {


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
    private BlogStarService blogStarService;

    private Map<String, Object> entry;

    private Integer page;

    private MongoCursor<Document> pagination;

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

        return this.head.initialize("Blog Star", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> data) {

        this.page = 1;

        this.pagination = this.blogStarService.loadBlogStarPagination(request, this.preloodeAccount, this.page);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-star";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public String url(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.entry = this.blogStarService.loadBlogStarEntry(pathVariable.get("url").toString());

        if (!this.entry.isEmpty()) {

            data.put("entry", this.entry);

            return "blog-star-view";

        }

        return "page-not-found";

    }


}
