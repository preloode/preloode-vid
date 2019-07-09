package com.preloode.vid.service;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Client;
import com.preloode.vid.component.DateTime;
import com.preloode.vid.component.RsaEncryption;
import com.preloode.vid.repository.MemberLogRepository;
import com.preloode.vid.repository.MemberRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class MemberService {


    @Autowired
    private Client client;

    @Autowired
    private DateTime dateTime;

    @Autowired
    private RsaEncryption rsaEncryption;

    @Autowired
    private MemberLogService memberLogService;

    @Autowired
    private MemberLogRepository memberLogRepository;

    @Autowired
    private MemberRepository memberRepository;


    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Login failed");
                put("result", false);
            }

        };

        String authentication = this.rsaEncryption.encrypt(request, "preloode" + this.dateTime.getTimestamp(request));

        Document findEq = new Document("username", data.get("username"));
        MongoCursor<Document> memberIterator = this.memberRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (memberIterator.hasNext()) {

            Map<String, Object> memberMap = memberIterator.next();

            Map<String, Object> memberPasswordMap = (Map<String, Object>) memberMap.get("password");

            String memberMainPassword = this.rsaEncryption.decrypt(request, memberPasswordMap.get("main").toString());
            String memberRecoveryPassword = this.rsaEncryption.decrypt(request, memberPasswordMap.get("recovery").toString());
            String dataPassword = this.rsaEncryption.decrypt(request, data.get("password").toString());

            if (memberMainPassword.equals(dataPassword) || memberRecoveryPassword.equals(dataPassword)) {

                if (memberMap.get("status").equals("Active")) {

                    findEq = new Document("member.id", memberMap.get("_id"));
                    MongoCursor<Document> memberLogIterator = this.memberLogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

                    if (memberLogIterator.hasNext()) {

                        Map<String, Object> memberLogMap = memberLogIterator.next();

                        if (memberLogMap.get("log").equals("Login")) {

                            Document insertOneData = new Document("authentication", memberLogMap.get("authentication"))
                                    .append("client", new Document("browser_id", this.client.getBrowser().get("id"))
                                            .append("browser_manufacturer", this.client.getBrowser().get("manufacturer"))
                                            .append("browser_name", this.client.getBrowser().get("name"))
                                            .append("browser_rendering_engine", this.client.getBrowser().get("renderingEngine"))
                                            .append("browser_type", this.client.getBrowser().get("type"))
                                            .append("browser_version", this.client.getBrowser().get("version"))
                                            .append("device_id", this.client.getDevice().get("id"))
                                            .append("device_manufacturer", this.client.getDevice().get("manufacturer"))
                                            .append("device_operating_system", this.client.getDevice().get("operatingSystem"))
                                            .append("device_type", this.client.getDevice().get("type"))
                                            .append("id", this.client.getId())
                                            .append("ip", this.client.getIp())
                                            .append("mac", this.client.getMac()))
                                    .append("description", "")
                                    .append("log", "Logout")
                                    .append("member", new Document("id", memberMap.get("_id"))
                                            .append("username", memberMap.get("username")))
                                    .append("target", new Document("id", memberMap.get("_id"))
                                            .append("name", memberMap.get("username")));
                            Document insertOneAdministrator = new Document("id", "0")
                                    .append("username", "System");
                            this.memberLogRepository.insertOne(request, insertOneData, insertOneAdministrator);

                        }

                    }

                    Document insertOneData = new Document("authentication", authentication)
                            .append("client", new Document("browser_id", this.client.getBrowser().get("id"))
                                    .append("browser_manufacturer", this.client.getBrowser().get("manufacturer"))
                                    .append("browser_name", this.client.getBrowser().get("name"))
                                    .append("browser_rendering_engine", this.client.getBrowser().get("renderingEngine"))
                                    .append("browser_type", this.client.getBrowser().get("type"))
                                    .append("browser_version", this.client.getBrowser().get("version"))
                                    .append("device_id", this.client.getDevice().get("id"))
                                    .append("device_manufacturer", this.client.getDevice().get("manufacturer"))
                                    .append("device_operating_system", this.client.getDevice().get("operatingSystem"))
                                    .append("device_type", this.client.getDevice().get("type"))
                                    .append("id", this.client.getId())
                                    .append("ip", this.client.getIp())
                                    .append("mac", this.client.getMac()))
                            .append("description", "")
                            .append("log", "Login")
                            .append("member", new Document("id", memberMap.get("_id"))
                                    .append("username", memberMap.get("username")))
                            .append("target", new Document("id", memberMap.get("_id"))
                                    .append("name", memberMap.get("username")));
                    Document insertOneAdministrator = new Document("id", "0")
                            .append("username", "System");
                    this.memberLogRepository.insertOne(request, insertOneData, insertOneAdministrator);

                    Cookie cookie = new Cookie("preloode_account", authentication);
                    cookie.setPath("/");
                    cookie.setMaxAge(365 * 24 * 60 * 60);
                    response.addCookie(cookie);

                    result.put("result", true);

                } else {

                    result.put("response", "Account Inactivated");

                }

            } else {

                result.put("response", "Invalid Password");

            }

        } else {

            result.put("response", "Username doesn't exist");

        }

        return result;

    }


    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Logout failed");
                put("result", false);
            }

        };

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            Boolean logout = false;

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("preloode_account")) {

                    Document findEq = new Document("authentication", cookie.getValue());
                    MongoCursor<Document> memberLogIterator = this.memberLogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

                    if (memberLogIterator.hasNext()) {

                        Map<String, Object> memberLogMap = memberLogIterator.next();

                        if (memberLogMap.get("log").equals("Login")) {

                            Map<String, Object> memberLogMemberMap = (Map<String, Object>) memberLogMap.get("member");

                            Document insertOneData = new Document("authentication", memberLogMap.get("authentication"))
                                    .append("client", new Document("browser_id", this.client.getBrowser().get("id"))
                                            .append("browser_manufacturer", this.client.getBrowser().get("manufacturer"))
                                            .append("browser_name", this.client.getBrowser().get("name"))
                                            .append("browser_rendering_engine", this.client.getBrowser().get("renderingEngine"))
                                            .append("browser_type", this.client.getBrowser().get("type"))
                                            .append("browser_version", this.client.getBrowser().get("version"))
                                            .append("device_id", this.client.getDevice().get("id"))
                                            .append("device_manufacturer", this.client.getDevice().get("manufacturer"))
                                            .append("device_operating_system", this.client.getDevice().get("operatingSystem"))
                                            .append("device_type", this.client.getDevice().get("type"))
                                            .append("id", this.client.getId())
                                            .append("ip", this.client.getIp())
                                            .append("mac", this.client.getMac()))
                                    .append("description", "")
                                    .append("log", "Logout")
                                    .append("member", new Document("id", memberLogMemberMap.get("id"))
                                            .append("username", memberLogMemberMap.get("username")))
                                    .append("target", new Document("id", memberLogMemberMap.get("_id"))
                                            .append("name", memberLogMemberMap.get("username")));
                            Document insertOneAdministrator = new Document("id", "0")
                                    .append("username", "System");
                            this.memberLogRepository.insertOne(request, insertOneData, insertOneAdministrator);

                            result.put("result", true);

                            logout = true;

                        }

                    }

                }

            }

            if (logout) {

                for (Cookie cookie : cookies) {

                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);

                }

            }

        }

        return result;

    }


    public Map<String, Object> register(HttpServletRequest request, Document data) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("response", "Register failed");
                put("result", false);
            }

        };

        Map<String, Object> dataContactMap = (Map<String, Object>) data.get("contact");

        dataContactMap.put("bbm_pin", "");
        dataContactMap.put("line_id", "");
        dataContactMap.put("wechat_id", "");
        dataContactMap.put("whatsapp_number", "");

        data.append("city", "")
                .append("contact", dataContactMap)
                .append("country", "")
                .append("file", "")
                .append("game", new Document("account", new Document("password", new ArrayList<String>())
                        .append("username", new ArrayList<String>()))
                        .append("id", new ArrayList<String>())
                        .append("name", new ArrayList<String>())
                        .append("type", new Document("id", new ArrayList<String>())
                                .append("name", new ArrayList<String>())))
                .append("group", new Document("id", "")
                        .append("name", ""))
                .append("identity", "")
                .append("language", "")
                .append("name", new Document("first", "")
                        .append("last", "")
                        .append("middle", ""))
                .append("payment", new Document("bank", new Document("account", new Document("id", new ArrayList<String>())
                        .append("name", new ArrayList<String>())
                        .append("number", new ArrayList<String>()))
                        .append("id", new ArrayList<String>())
                        .append("name", new ArrayList<String>()))
                        .append("method", "Bank Account Transfer"))
                .append("province", "")
                .append("status", "Active")
                .append("street_address", "")
                .append("type", new ArrayList<String>() {

                    {
                        add("Member");
                    }

                })
                .append("zip_code", "");

        Map<String, Object> validation = new HashMap<String, Object>() {

            {
                put("emailAddress", true);
                put("phoneNumber", true);
                put("username", false);
            }

        };

        Document findEq = new Document("username", data.get("username"));
        MongoCursor<Document> memberValidationIterator = this.memberRepository.findEqSort(findEq, new Document("created.timestamp", -1));

        if (!memberValidationIterator.hasNext()) {

            validation.put("username", true);

        } else {

            result.put("response", "Username already exist");

        }

        Map<String, Object> dataContact = (Map<String, Object>) data.get("contact");

        if (!dataContact.get("email_address").equals("")) {

            findEq = new Document("contact.email_address", dataContact.get("email_address"));
            memberValidationIterator = this.memberRepository.findEqSort(findEq, new Document("created.timestamp", -1));

            if (memberValidationIterator.hasNext()) {

                validation.put("emailAddress", false);
                result.put("response", "Email address already exist");

            }

        }

        if (!dataContact.get("phone_number").equals("")) {

            findEq = new Document("contact.phone_number", dataContact.get("phone_number"));
            memberValidationIterator = this.memberRepository.findEqSort(findEq, new Document("created.timestamp", -1));

            if (memberValidationIterator.hasNext()) {

                validation.put("phoneNumber", false);
                result.put("response", "Phone number already exist");

            }

        }

        Boolean valid = true;

        for (Map.Entry<String, Object> map : validation.entrySet()) {

            if (map.getValue().equals(false)) {

                valid = false;

                break;

            }

        }

        if (valid) {

            Document insertOneData = data;
            Document insertOneAdministrator = new Document("id", "0")
                    .append("username", "System");
            String memberInsertId = this.memberRepository.insertOne(request, insertOneData, insertOneAdministrator);

            Document insertLogTarget = new Document("target", new Document("id", memberInsertId)
                    .append("name", data.get("username")));
            this.memberLogService.insert(request, insertOneAdministrator, "", "Register", insertLogTarget);

            result.put("response", "Register success");
            result.put("result", true);

        }

        return result;

    }


}
