package com.preloode.vid.component;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.repository.MemberLogRepository;
import com.preloode.vid.repository.MemberRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Component
public class Account {


    @Autowired
    private Client client;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLogRepository memberLogRepository;


    public Boolean checkLogin(HttpServletRequest request) {

        Boolean result = false;

        this.client.getDetail(request);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("preloode_account")) {

                    Document findEq = new Document("authentication", cookie.getValue());
                    MongoCursor<Document> memberLogIterator = this.memberLogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

                    while (memberLogIterator.hasNext()) {

                        Map<String, Object> memberLogMap = memberLogIterator.next();

                        Map<String, Object> memberLogClientMap = (Map<String, Object>) memberLogMap.get("client");

                        if (memberLogMap.get("log").equals("Login") && memberLogClientMap.get("browser_id").equals(this.client.getBrowser().get("id")) && memberLogClientMap.get("browser_manufacturer").equals(this.client.getBrowser().get("manufacturer")) && memberLogClientMap.get("browser_name").equals(this.client.getBrowser().get("name")) && memberLogClientMap.get("browser_rendering_engine").equals(this.client.getBrowser().get("renderingEngine")) && memberLogClientMap.get("browser_type").equals(this.client.getBrowser().get("type")) && memberLogClientMap.get("browser_version").equals(this.client.getBrowser().get("version")) && memberLogClientMap.get("device_id").equals(this.client.getDevice().get("id")) && memberLogClientMap.get("device_manufacturer").equals(this.client.getDevice().get("manufacturer")) && memberLogClientMap.get("device_operating_system").equals(this.client.getDevice().get("operatingSystem")) && memberLogClientMap.get("device_type").equals(this.client.getDevice().get("type")) && memberLogClientMap.get("id").equals(this.client.getId()) && memberLogClientMap.get("ip").equals(this.client.getIp()) && memberLogClientMap.get("mac").equals(this.client.getMac())) {

                            result = true;

                        } else if (memberLogMap.get("log").equals("Logout")) {

                            result = false;

                        }

                    }

                    break;

                }

            }

        }

        return result;

    }


    public Map<String, Object> initializeAccount(HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
            }

        };

        Cookie[] cookies = request.getCookies();

        this.client.getDetail(request);

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("preloode_account")) {

                    Boolean login = false;

                    Document findEq = new Document("authentication", cookie.getValue());
                    MongoCursor<Document> memberLogIterator = this.memberLogRepository.findEqSort(findEq, new Document("created.timestamp", -1));

                    Map<String, Object> memberLogLogin = new HashMap<String, Object>();

                    while (memberLogIterator.hasNext()) {

                        Map<String, Object> memberLogMap = memberLogIterator.next();

                        Map<String, Object> memberLogClientMap = (Map<String, Object>) memberLogMap.get("client");

                        if (memberLogMap.get("log").equals("Login") && memberLogClientMap.get("browser_id").equals(this.client.getBrowser().get("id")) && memberLogClientMap.get("browser_manufacturer").equals(this.client.getBrowser().get("manufacturer")) && memberLogClientMap.get("browser_name").equals(this.client.getBrowser().get("name")) && memberLogClientMap.get("browser_rendering_engine").equals(this.client.getBrowser().get("renderingEngine")) && memberLogClientMap.get("browser_type").equals(this.client.getBrowser().get("type")) && memberLogClientMap.get("browser_version").equals(this.client.getBrowser().get("version")) && memberLogClientMap.get("device_id").equals(this.client.getDevice().get("id")) && memberLogClientMap.get("device_manufacturer").equals(this.client.getDevice().get("manufacturer")) && memberLogClientMap.get("device_operating_system").equals(this.client.getDevice().get("operatingSystem")) && memberLogClientMap.get("device_type").equals(this.client.getDevice().get("type")) && memberLogClientMap.get("id").equals(this.client.getId()) && memberLogClientMap.get("ip").equals(this.client.getIp()) && memberLogClientMap.get("mac").equals(this.client.getMac())) {

                            memberLogLogin = memberLogMap;

                            login = true;

                        } else if (memberLogMap.get("log").equals("Logout")) {

                            login = false;

                        }

                    }

                    if (login) {

                        Map<String, Object> memberLogMemberMap = (Map<String, Object>) memberLogLogin.get("member");

                        findEq = new Document("_id", memberLogMemberMap.get("id").toString());
                        MongoCursor<Document> memberIterator = this.memberRepository.findEqSort(findEq, new Document("created.timestamp", -1));

                        if (memberIterator.hasNext()) {

                            result = memberIterator.next();
                            result.put("result", true);

                        }

                    }

                    break;

                }

            }

        }

        return result;

    }


}
