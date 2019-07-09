package com.preloode.vid.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class Filter {


    @Autowired
    private DateTime dateTime;

    @Autowired
    private Log log;


    public Map<String, Object> load(HttpServletRequest request, Map<String, Object> account) {

        Map<String, Object> result = new HashMap<String, Object>();

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(account.get("cookie"))) {

                    Gson gson = new Gson();

                    Map<String, Object> filterCookie = gson.fromJson(cookie.getValue(), new TypeToken<Map<String, Object>>() {
                    }.getType());

                    for (Map.Entry<String, Object> map : filterCookie.entrySet()) {

                        if (map.getKey().equals(account.get("id"))) {

                            Map<String, Object> filterMap = (Map<String, Object>) map.getValue();

                            for (Map.Entry<String, Object> mapLevel1 : filterMap.entrySet()) {

                                result.put(mapLevel1.getKey().replace(".", "_"), mapLevel1.getValue());

                            }

                            break;

                        }

                    }

                    break;

                }

            }

        }

        return result;

    }


    public Document query(HttpServletRequest request, Map<String, Object> account) {

        Document result = new Document();

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(account.get("cookie"))) {

                    Gson gson = new Gson();

                    Map<String, Object> filterCookie = gson.fromJson(cookie.getValue(), new TypeToken<Map<String, Object>>() {
                    }.getType());

                    Map<String, Object> filterAccount = new HashMap<String, Object>();

                    for (Map.Entry<String, Object> map : filterCookie.entrySet()) {

                        if (map.getKey().equals(account.get("id"))) {

                            filterAccount = (Map<String, Object>) map.getValue();

                            break;

                        }

                    }

                    if (filterAccount.containsKey("result")) {

                        for (Map.Entry<String, Object> map : filterAccount.entrySet()) {

                            if (!map.getKey().equals("result")) {

                                ArrayList<String> filterValue = (ArrayList<String>) map.getValue();

                                if (filterValue.get(0).equals("equal")) {

                                    if (!filterValue.get(1).isEmpty()) {

                                        result.append(map.getKey(), new Document("$eq", filterValue.get(1)));

                                    }

                                } else if (filterValue.get(0).equals("like")) {

                                    if (!filterValue.get(1).isEmpty()) {

                                        result.append(map.getKey(), new Document("$regex", "(?i)" + filterValue.get(1) + "(?-i)"));

                                    }

                                } else if (filterValue.get(0).equals("between")) {

                                    if (filterValue.size() > 3) {

                                        if (filterValue.get(1).equals("date")) {

                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            this.dateTime.setTimezone(simpleDateFormat);

                                            Date start = null;
                                            Date end = null;

                                            if (!filterValue.get(2).isEmpty()) {

                                                try {

                                                    start = new Date(simpleDateFormat.parse(filterValue.get(2) + " 00:00:00").getTime());

                                                } catch (Exception exception) {

                                                    this.log.exception(request, exception);

                                                }

                                            }

                                            if (!filterValue.get(3).isEmpty()) {

                                                try {

                                                    end = new Date(simpleDateFormat.parse(filterValue.get(3) + " 23:59:59").getTime());

                                                } catch (Exception exception) {

                                                    this.log.exception(request, exception);

                                                }

                                            }

                                            Document between = new Document();

                                            if (start != null) {

                                                between.append("$gte", start);

                                            }

                                            if (end != null) {

                                                between.append("$lte", end);

                                            }

                                            if (between.size() > 0) {

                                                result.append(map.getKey(), between);

                                            }

                                        } else {

                                            Document between = new Document();

                                            if (!filterValue.get(2).isEmpty()) {

                                                between.append("$gte", filterValue.get(2));

                                            }

                                            if (!filterValue.get(3).isEmpty()) {

                                                between.append("$lte", filterValue.get(3));

                                            }

                                            if (between.size() > 0) {

                                                result.append(map.getKey(), between);

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                    break;

                }

            }

        }

        return result;

    }


    public Boolean remove(HttpServletRequest request, HttpServletResponse response, Map<String, Object> account) {

        Boolean result = false;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(account.get("cookie"))) {

                    Gson gson = new Gson();

                    Map<String, Object> filterCookie = gson.fromJson(cookie.getValue(), new TypeToken<Map<String, Object>>() {
                    }.getType());

                    if (filterCookie.containsKey(account.get("id").toString())) {

                        filterCookie.remove(account.get("id").toString());

                    }

                    Cookie filter = new Cookie(account.get("cookie").toString(), new Gson().toJson(filterCookie));
                    filter.setPath("/");
                    filter.setMaxAge(30 * 24 * 60 * 60);
                    response.addCookie(filter);

                    break;

                }

            }

        }

        result = true;

        return result;

    }


    public Boolean write(HttpServletResponse response, Map<String, Object> account, Document data) {

        Boolean result = false;

        data.put("result", true);
        Document filter = new Document(account.get("id").toString(), data);
        Cookie cookie = new Cookie(account.get("cookie").toString(), new Gson().toJson(filter));
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);

        result = true;

        return result;

    }


}
