package com.preloode.vid.service;

import com.preloode.vid.component.Client;
import com.preloode.vid.repository.MemberLogRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Service
public class MemberLogService {


    @Autowired
    private Client client;

    @Autowired
    private MemberLogRepository memberLogRepository;


    public String insert(HttpServletRequest request, Document member, String description, String log, Document target) {

        String result = "";

        this.client.getDetail(request);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("preloode_account")) {

                    Document insertOne = new Document("member", member)
                            .append("authentication", cookie.getValue())
                            .append("client", new Document("browser_id", client.getBrowser().get("id"))
                                    .append("browser_manufacturer", client.getBrowser().get("manufacturer"))
                                    .append("browser_name", client.getBrowser().get("name"))
                                    .append("browser_rendering_engine", client.getBrowser().get("renderingEngine"))
                                    .append("browser_type", client.getBrowser().get("type"))
                                    .append("browser_version", client.getBrowser().get("version"))
                                    .append("device_id", client.getDevice().get("id"))
                                    .append("device_manufacturer", client.getDevice().get("manufacturer"))
                                    .append("device_operating_system", client.getDevice().get("operatingSystem"))
                                    .append("device_type", client.getDevice().get("type"))
                                    .append("id", client.getId()))
                            .append("ip", client.getIp())
                            .append("mac", client.getMac())
                            .append("description", description)
                            .append("log", log)
                            .append("target", target);
                    Document insertOneAdministrator = new Document("id", "0")
                            .append("username", "System");
                    result = this.memberLogRepository.insertOne(request, insertOne, insertOneAdministrator);

                    break;

                }

            }

        }

        return result;

    }


}
