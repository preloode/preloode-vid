package com.preloode.vid.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.preloode.vid.configuration.Page;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class Pagination {


    @Autowired
    private Page page;

    private Map<String, String> attribute;

    private Map<String, String> css;

    private Integer next;

    private Integer previous;

    private String url;


    public Map<String, String> getAttribute() {

        Map<String, String> attribute = new HashMap<String, String>() {

            {
                put("button", "ng-click=\"goToPage($event)\"");
                put("form", "");
                put("input", "ng-keyup=\"goToPage($event)\" ng-model=\"site.page\"");
            }

        };
        this.setAttribute(attribute);

        return this.attribute;

    }


    public void setAttribute(Map<String, String> attribute) {

        this.attribute = attribute;

    }


    public Map<String, String> getCss() {

        Map<String, String> css = new HashMap<String, String>() {

            {
                put("button", "");
                put("form", "");
                put("input", "");
            }

        };
        this.setCss(css);

        return this.css;

    }


    public void setCss(Map<String, String> css) {

        this.css = css;

    }


    public Integer getNext() {

        Integer next = 5;
        this.setNext(next);

        return this.next;

    }


    public void setNext(Integer next) {

        this.next = next;

    }


    public Integer getPrevious() {

        Integer previous = 5;
        this.setPrevious(previous);

        return this.previous;

    }


    public void setPrevious(Integer previous) {

        this.previous = previous;

    }


    public String getUrl() {

        String url = "page-";
        this.setUrl(url);

        return this.url;

    }


    public void setUrl(String url) {

        this.url = url;

    }


    public String createLink(HttpServletRequest request, String url, Integer page, Integer size, long total) {

        String result = "";

        url = request.getRequestURI().replace(request.getServletPath(), url) + "/";
        Integer previous = page - this.getPrevious();
        Integer next = page + this.getNext();
        Integer max = (int) Math.ceil(total / size);

        if (max > 1) {

            result = "<ul>";

            if (previous < 1) {

                previous = 1;

            }

            if (page > this.getPrevious()) {

                result += "<a href=\"" + url + this.getUrl() + "1\"><li>First</li></a>";

            }

            if (page > 1) {

                result += "<a href=\"" + url + this.getUrl() + (page - 1) + "\"><li><i class=\"previous-white square-15\"></i></li></a>";

            }

            if (page > (this.getPrevious() + 1)) {

                result += "<li>...</li>";

            }

            if (page > 1) {

                for (Integer i = previous; i < page; i++) {

                    result += "<a href=\"" + url + this.getUrl() + i + "\"><li>" + i + "</li></a>";

                }

            }

            result += "<li>"
                    + "<form class=\"" + this.getCss().get("form") + "\" method=\"POST\" action=\"\" " + this.getAttribute().get("form") + ">"
                    + "<input class=\"" + this.getCss().get("input") + "\" name=\"page\" " + this.getAttribute().get("input") + " />"
                    + "<button class=\"" + this.getCss().get("button") + "\" name=\"go-to-page\" " + this.getAttribute().get("button") + ">Go</button>"
                    + "</form>"
                    + "</li>";

            if (next > max) {

                next = max;

            }

            if (page < next) {

                for (Integer i = (page + 1); i <= next; i++) {

                    result += "<a href=\"" + url + this.getUrl() + i + "\"><li>" + i + "</li></a>";

                }

                if (next < (max - 1)) {

                    result += "<li>...</li>";

                }

                if (page < max) {

                    result += "<a href=\"" + url + this.getUrl() + (page + 1) + "\"><li><i class=\"next-white square-15\"></i></li></a>";

                }

                if (next < max) {

                    result += "<a href=\"" + url + this.getUrl() + max + "\"><li>Last</li></a>";

                }

            }

            result += "</ul>";

        }

        return result;

    }


    public String load(HttpServletRequest request, Map<String, Object> account) {

        String result = this.page.getSize().toString();

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(account.get("cookie"))) {

                    Gson gson = new Gson();

                    Map<String, Object> paginationCookie = gson.fromJson(cookie.getValue(), new TypeToken<Map<String, Object>>() {
                    }.getType());

                    for (Map.Entry<String, Object> map : paginationCookie.entrySet()) {

                        if (map.getKey().equals(account.get("id"))) {

                            result = map.getValue().toString();

                            break;

                        }

                    }

                    break;

                }

            }

        }

        return result;

    }


    public Boolean set(HttpServletResponse response, Map<String, Object> account, String pagination) {

        Boolean result = false;

        pagination = pagination.replaceAll("[^0-9]", "");

        if (Integer.parseInt(pagination) < 1) {

            pagination = "1";

        }

        Document paginationDocument = new Document(account.get("id").toString(), pagination);
        Cookie cookie = new Cookie(account.get("cookie").toString(), new Gson().toJson(paginationDocument));
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);

        result = true;

        return result;

    }


}
