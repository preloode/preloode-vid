package com.preloode.vid.component;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.configuration.Url;
import com.preloode.vid.repository.SettingRepository;
import eu.bitwalker.useragentutils.UserAgent;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;


@Component
public class Client {


    @Autowired
    private Url url;

    @Autowired
    private Log log;

    @Autowired
    private SettingRepository settingRepository;

    private Map<String, String> browser;

    private Map<String, String> device;

    private String id;

    private String ip;

    private String jSessionId;

    private String mac;


    public Map<String, String> getBrowser() {

        return this.browser;

    }


    public void setBrowser(Map<String, String> browser) {

        this.browser = browser;

    }


    public Map<String, String> getDevice() {

        return this.device;

    }


    public void setDevice(Map<String, String> device) {

        this.device = device;

    }


    public String getId() {

        return this.id;

    }


    public void setId(String id) {

        this.id = id;

    }


    public String getIp() {

        return this.ip;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public String getJSessionId() {

        return this.jSessionId;

    }


    public void setJSessionId(String jSessionId) {

        this.jSessionId = jSessionId;

    }


    public String getMac() {

        return this.mac;

    }


    public void setMac(String mac) {

        this.mac = mac;

    }


    public void checkAccess(HttpServletRequest request, HttpServletResponse response) {

        MongoCursor<Document> settingIterator = this.settingRepository.findSort(new Document("created.timestamp", -1));

        if (settingIterator.hasNext()) {

            Map<String, Object> settingMap = settingIterator.next();

            if (settingMap.get("installation").equals("Uninstalled")) {

                try {

                    response.setHeader("Location", this.url.getBase() + "/installation/");
                    response.setStatus(302);

                } catch (Exception exception) {

                    this.log.exception(request, exception);

                }

            } else if (settingMap.get("status").equals("Under Construction")) {

                try {

                    response.setHeader("Location", this.url.getBase() + "/under-construction/");
                    response.setStatus(302);

                } catch (Exception exception) {

                    this.log.exception(request, exception);

                }

            }

        }

    }


    public void getDetail(HttpServletRequest request) {

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        Map<String, String> browser = new HashMap<String, String>();
        browser.put("id", Integer.toString(userAgent.getBrowser().getId()));
        browser.put("name", userAgent.getBrowser().getName());
        browser.put("manufacturer", userAgent.getBrowser().getManufacturer().getName());
        browser.put("renderingEngine", userAgent.getBrowser().getRenderingEngine().getName());
        browser.put("type", userAgent.getBrowser().getBrowserType().getName());
        browser.put("version", userAgent.getBrowserVersion().getVersion());
        this.setBrowser(browser);

        Map<String, String> device = new HashMap<String, String>();
        device.put("id", Integer.toString(userAgent.getOperatingSystem().getId()));
        device.put("manufacturer", userAgent.getOperatingSystem().getManufacturer().getName());
        device.put("operatingSystem", userAgent.getOperatingSystem().getName());
        device.put("type", userAgent.getOperatingSystem().getDeviceType().getName());
        this.setDevice(device);

        this.setId(Integer.toString(userAgent.getId()));

        String ip = request.getHeader("Remote_Addr");

        if (ip == null) {

            ip = request.getHeader("X_FORWARDED_FOR");

        }

        if (ip == null) {

            ip = request.getRemoteAddr();

        }

        this.setIp(ip);

        this.setMac("");

        try {

            InetAddress inetAddress = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
            byte[] hardwareAddress = network.getHardwareAddress();

            StringBuilder stringBuilder = new StringBuilder();

            for (Integer i = 0; i < hardwareAddress.length; i++) {

                stringBuilder.append(String.format("%02X%s", hardwareAddress[i], (i < hardwareAddress.length - 1) ? "-" : ""));

            }

            this.setMac(stringBuilder.toString());

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("JSESSIONID")) {

                    this.setJSessionId(cookie.getValue());

                    break;

                }

            }

        }

    }


}
