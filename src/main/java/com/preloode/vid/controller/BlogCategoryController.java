package com.preloode.vid.controller;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Account;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Path;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.service.BlogCategoryService;
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
@RequestMapping(value = "/category")
public class BlogCategoryController {


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
    private BlogCategoryService blogCategoryService;

    private MongoCursor<Document> blogPagination;

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

        return this.head.initialize("Blog Category", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> data) {

        this.page = 1;

        this.pagination = this.blogCategoryService.loadBlogCategoryPagination(request, this.preloodeAccount, "", this.page);

        this.blogPagination = this.blogCategoryService.loadBlogPagination(request, this.preloodeAccount, "", this.page);

        if (pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public String url(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}/{url1}", method = RequestMethod.GET)
    public String urlLevel1(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}/{url1}/{url2}", method = RequestMethod.GET)
    public String urlLevel2(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}/{url1}/{url2}/{url3}", method = RequestMethod.GET)
    public String urlLevel3(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}/{url1}/{url2}/{url3}/{url4}", method = RequestMethod.GET)
    public String urlLevel4(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url}/{url1}/{url2}/{url3}/{url4}/{url5}", method = RequestMethod.GET)
    public String urlLevel5(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        String redirect = this.initializeData(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog-category";

        } else if (this.blogPagination.hasNext()) {

            return "redirect:/video/" + redirect + "/";

        }

        return "page-not-found";

    }


    private String initializeData(HttpServletRequest request, Map<String, Object> pathVariable) {

        String result = "";

        for (Map.Entry<String, Object> map : pathVariable.entrySet()) {

            result += "/" + map.getValue();

        }

        result = result.substring(1);
        this.pagination = this.blogCategoryService.loadBlogCategoryPagination(request, this.preloodeAccount, result, this.page);

        this.blogPagination = this.blogCategoryService.loadBlogPagination(request, this.preloodeAccount, result, this.page);

        return result;

    }


}
