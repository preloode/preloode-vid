package com.preloode.vid.component;

import com.mongodb.client.MongoCursor;
import com.preloode.vid.configuration.Setting;
import com.preloode.vid.repository.PageRepository;
import com.preloode.vid.repository.SettingRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;


@Component
public class Head {


    @Autowired
    private Setting setting;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private SettingRepository settingRepository;


    public Setting initialize(String name, String url) {

        ArrayList<String> css = new ArrayList<String>();
        css.add(name.replaceAll(" ", "-").toLowerCase() + ".css");
        this.setting.setCss(css);

        ArrayList<String> javascript = new ArrayList<String>();
        javascript.add(name.replaceAll(" ", "-").toLowerCase() + ".js");
        this.setting.setJavascript(javascript);

        this.setting.setMetaTitle(name);

        MongoCursor<Document> settingIterator = this.settingRepository.findSort(new Document("created.timestamp", -1));

        if (settingIterator.hasNext()) {

            Map<String, Object> settingMap = settingIterator.next();

            Map<String, Object> settingFileMap = (Map<String, Object>) settingMap.get("file");

            if (!settingFileMap.get("favicon").toString().isEmpty()) {

                this.setting.setFavicon(settingFileMap.get("favicon").toString());

            }

            if (!settingFileMap.get("logo").toString().isEmpty()) {

                this.setting.setLogo(settingFileMap.get("logo").toString());

            }

            Map<String, Object> settingMetaMap = (Map<String, Object>) settingMap.get("meta");

            if (!settingMetaMap.get("description").toString().isEmpty()) {

                this.setting.setMetaDescription(settingMetaMap.get("description").toString());

            }

            if (!settingMetaMap.get("keyword").toString().isEmpty()) {

                this.setting.setMetaKeyword(settingMetaMap.get("keyword").toString());

            }

            if (!settingMetaMap.get("title").toString().isEmpty()) {

                this.setting.setMetaTitle(settingMetaMap.get("title").toString());

            }

        }

        MongoCursor<Document> pageIterator = this.pageRepository.findEqSort(new Document("name", name), new Document("created.timestamp", -1));

        if (pageIterator.hasNext()) {

            Map<String, Object> pageMap = pageIterator.next();

            Map<String, Object> pageContentMap = (Map<String, Object>) pageMap.get("content");

            Map<String, Object> pageContentUrlMap = (Map<String, Object>) pageContentMap.get("url");

            ArrayList<String> pageContentUrlUrlList = (ArrayList<String>) pageContentUrlMap.get("url");

            ArrayList<String> pageContentValueList = (ArrayList<String>) pageContentMap.get("value");

            for (Integer i = 0; i < pageContentUrlUrlList.size(); i++) {

                if (pageContentUrlUrlList.get(i).equals(url)) {

                    if (!pageContentValueList.get(i).isEmpty()) {

                        this.setting.setContent(pageContentValueList.get(i));

                    }

                    break;

                }

            }

            Map<String, Object> pageDescriptionMap = (Map<String, Object>) pageMap.get("description");

            Map<String, Object> pageDescriptionUrlMap = (Map<String, Object>) pageDescriptionMap.get("url");

            ArrayList<String> pageDescriptionUrlUrlList = (ArrayList<String>) pageDescriptionUrlMap.get("url");

            ArrayList<String> pageDescriptionValueList = (ArrayList<String>) pageDescriptionMap.get("value");

            for (Integer i = 0; i < pageDescriptionUrlUrlList.size(); i++) {

                if (pageDescriptionUrlUrlList.get(i).equals(url)) {

                    if (!pageDescriptionValueList.get(i).isEmpty()) {

                        this.setting.setDescription(pageDescriptionValueList.get(i));

                    }

                    break;

                }

            }

            Map<String, Object> pageMetaMap = (Map<String, Object>) pageMap.get("meta");

            Map<String, Object> pageMetaDescriptionMap = (Map<String, Object>) pageMetaMap.get("description");

            Map<String, Object> pageMetaDescriptionUrlMap = (Map<String, Object>) pageMetaDescriptionMap.get("url");

            ArrayList<String> pageMetaDescriptionUrlUrlList = (ArrayList<String>) pageMetaDescriptionUrlMap.get("url");

            ArrayList<String> pageMetaDescriptionValueList = (ArrayList<String>) pageMetaDescriptionMap.get("value");

            for (Integer i = 0; i < pageMetaDescriptionUrlUrlList.size(); i++) {

                if (pageMetaDescriptionUrlUrlList.get(i).equals(url)) {

                    if (!pageMetaDescriptionValueList.get(i).isEmpty()) {

                        this.setting.setMetaDescription(pageMetaDescriptionValueList.get(i));

                    }

                    break;

                }

            }

            Map<String, Object> pageMetaKeywordMap = (Map<String, Object>) pageMetaMap.get("keyword");

            Map<String, Object> pageMetaKeywordUrlMap = (Map<String, Object>) pageMetaKeywordMap.get("url");

            ArrayList<String> pageMetaKeywordUrlUrlList = (ArrayList<String>) pageMetaKeywordUrlMap.get("url");

            ArrayList<String> pageMetaKeywordValueList = (ArrayList<String>) pageMetaKeywordMap.get("value");

            for (Integer i = 0; i < pageMetaKeywordUrlUrlList.size(); i++) {

                if (pageMetaKeywordUrlUrlList.get(i).equals(url)) {

                    if (!pageMetaKeywordValueList.get(i).isEmpty()) {

                        this.setting.setMetaKeyword(pageMetaKeywordValueList.get(i));

                    }

                    break;

                }

            }

            Map<String, Object> pageMetaTitleMap = (Map<String, Object>) pageMetaMap.get("title");

            Map<String, Object> pageMetaTitleUrlMap = (Map<String, Object>) pageMetaTitleMap.get("url");

            ArrayList<String> pageMetaTitleUrlUrlList = (ArrayList<String>) pageMetaTitleUrlMap.get("url");

            ArrayList<String> pageMetaTitleValueList = (ArrayList<String>) pageMetaTitleMap.get("value");

            for (Integer i = 0; i < pageMetaTitleUrlUrlList.size(); i++) {

                if (pageMetaTitleUrlUrlList.get(i).equals(url)) {

                    if (!pageMetaTitleValueList.get(i).isEmpty()) {

                        this.setting.setMetaTitle(pageMetaTitleValueList.get(i));

                    }

                    break;

                }

            }

        }

        return this.setting;

    }


}
