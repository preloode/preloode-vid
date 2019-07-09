package com.preloode.vid.component;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Component
public class Layout {


    public Map<String, Object> initializeMenu(HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
            }

        };

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("preloode_main_menu")) {

                    if (cookie.getValue().equals("Wide Open")) {

                        result.put("mainMenuTranslateX", 0);
                        result.put("mainMenuIconTranslateX", 0);

                    } else {

                        result.put("mainMenuTranslateX", "-100%");
                        result.put("mainMenuIconTranslateX", "-250px");

                    }

                }

            }

        }

        return result;

    }


}
