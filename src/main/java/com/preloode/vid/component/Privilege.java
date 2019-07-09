package com.preloode.vid.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class Privilege {


    public Boolean check(Map<String, Object> account, String privilege, String action) {

        Boolean privilegeCheck = false;

        Map<String, Object> privilegeMap = (Map<String, Object>) account.get("privilege");

        if (action.equals("view") && privilegeMap.get(privilege).toString().charAt(0) == '7') {

            privilegeCheck = true;

        } else if (action.equals("add") && privilegeMap.get(privilege).toString().charAt(1) == '7') {

            privilegeCheck = true;

        } else if (action.equals("edit") && privilegeMap.get(privilege).toString().charAt(2) == '7') {

            privilegeCheck = true;

        } else if (action.equals("delete") && privilegeMap.get(privilege).toString().charAt(3) == '7') {

            privilegeCheck = true;

        }

        return privilegeCheck;

    }


    public Map<String, Object> menu(Map<String, Object> account) {

        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> privilege = (Map<String, Object>) account.get("privilege");

        for (Map.Entry<String, Object> map : privilege.entrySet()) {

            result.put(map.getKey(), map.getValue().toString().charAt(0));

        }

        return result;

    }


}
