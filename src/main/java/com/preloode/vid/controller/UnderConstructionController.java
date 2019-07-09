package com.preloode.vid.controller;

import com.preloode.vid.component.Head;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping(value = "/under-construction")
public class UnderConstructionController {


    @Autowired
    private Url url;

    @Autowired
    private Head head;


    @ModelAttribute("setting")
    public Setting setting(HttpServletRequest request) {

        return this.head.initialize("Under Construction", request.getServerName());

    }


    @ModelAttribute("url")
    public Url url() {

        return this.url;

    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Map<String, Object> data) {

        return "under-construction";

    }


}
