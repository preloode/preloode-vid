package com.preloode.vid.controller;

import com.preloode.vid.service.MemberService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/member")
public class MemberController {


    @Autowired
    private MemberService memberService;


    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Document data) {

        return this.memberService.login(request, response, data);

    }


    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {

        return this.memberService.logout(request, response);

    }


    @RequestMapping(value = "/register")
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request, HttpSession session, @RequestBody Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Register failed");
                put("result", false);
            }

        };

        String captcha = session.getAttribute("captcha").toString();

        if (data.get("captcha").equals(captcha)) {

            data.remove("captcha");

            result = this.memberService.register(request, data);

        } else {

            result.put("response", "Captcha doesn't match to image");

        }

        return result;

    }


}
