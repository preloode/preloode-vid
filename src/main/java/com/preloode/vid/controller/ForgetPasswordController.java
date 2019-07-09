package com.preloode.vid.controller;

import com.preloode.vid.component.Account;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Path;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping(value = "/forget-password")
public class ForgetPasswordController {


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
    private ForgetPasswordService forgetPasswordService;

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

        return this.head.initialize("Forget Password", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> data) {

        this.client.checkAccess(request, response);

        this.preloodeAccount = this.account.initializeAccount(request);

        return "forget-password";

    }


}
