package com.preloode.vid.controller;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Account;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Path;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.service.BlogService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/video")
public class BlogController {


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
    private BlogService blogService;

    private MongoCursor<Document> comment;

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

        this.client.getDetail(request);

        this.preloodeAccount = this.account.initializeAccount(request);

        return this.preloodeAccount;

    }


    @ModelAttribute("setting")
    public Setting setting(HttpServletRequest request) {

        return this.head.initialize("Blog", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> data) {

        this.page = 1;

        this.pagination = this.blogService.loadBlogPagination(request, this.preloodeAccount, "", this.page);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}", method = RequestMethod.GET)
    public String url(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}/{url1}", method = RequestMethod.GET)
    public String urlLevel1(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}/{url1}/{url2}", method = RequestMethod.GET)
    public String urlLevel2(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}/{url1}/{url2}/{url3}", method = RequestMethod.GET)
    public String urlLevel3(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}/{url1}/{url2}/{url3}/{url4}", method = RequestMethod.GET)
    public String urlLevel4(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/{url:^(?!watch).+}/{url1}/{url2}/{url3}/{url4}/{url5}", method = RequestMethod.GET)
    public String urlLevel5(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializePagination(request, pathVariable);

        if (this.pagination.hasNext()) {

            data.put("pagination", this.pagination);

            return "blog";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch", method = RequestMethod.GET)
    public String watch(HttpServletRequest request, Map<String, Object> data) {

        this.page = 1;

        this.entry = this.blogService.loadBlogEntry("");

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}", method = RequestMethod.GET)
    public String watchUrl(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}/{url1}", method = RequestMethod.GET)
    public String watchUrlLevel1(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}/{url1}/{url2}", method = RequestMethod.GET)
    public String watchUrlLevel2(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}/{url1}/{url2}/{url3}", method = RequestMethod.GET)
    public String watchUrlLevel3(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}/{url1}/{url2}/{url3}/{url4}", method = RequestMethod.GET)
    public String watchUrlLevel4(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/watch/{url}/{url1}/{url2}/{url3}/{url4}/{url5}", method = RequestMethod.GET)
    public String watchUrlLevel5(HttpServletRequest request, @PathVariable Map<String, Object> pathVariable, Map<String, Object> data) {

        this.page = 1;

        this.initializeData(request, pathVariable);

        if (!this.entry.isEmpty()) {

            data.put("comment", this.comment);
            data.put("entry", this.entry);

            return "blog-watch";

        }

        return "page-not-found";

    }


    @RequestMapping(value = "/initialize-data")
    @ResponseBody
    public Map<String, Object> initializeData(@RequestBody Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
            }

        };

        result = this.blogService.initializeData(data);

        return result;

    }


    @RequestMapping(value = "/comment")
    @ResponseBody
    public Map<String, Object> comment(HttpServletRequest request, @RequestBody Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
            }

        };

        //result = this.blogService.sendComment(request, data);
        return result;

    }


    @RequestMapping(value = "/dislike")
    @ResponseBody
    public Map<String, Object> dislike(HttpServletRequest request, @RequestBody Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
            }

        };

        if (!this.preloodeAccount.isEmpty()) {

            result = this.blogService.dislike(request, this.preloodeAccount, data);

        } else {

            result.put("response", "You haven't login yet");

        }

        return result;

    }


    private void initializePagination(HttpServletRequest request, Map<String, Object> pathVariable) {

        String url = "";

        for (Map.Entry<String, Object> map : pathVariable.entrySet()) {

            url += "/" + map.getValue();

        }

        url = url.substring(1);
        this.pagination = this.blogService.loadBlogPagination(request, this.preloodeAccount, url, this.page);

    }


    private void initializeData(HttpServletRequest request, Map<String, Object> pathVariable) {

        String url = "";

        for (Map.Entry<String, Object> map : pathVariable.entrySet()) {

            url += "/" + map.getValue();

        }

        url = url.substring(1);
        this.entry = this.blogService.loadBlogEntry(url);

        this.blogService.updateView(request, this.client, url);

        this.comment = this.blogService.loadComment(request, this.entry.get("_id").toString());

    }


}
