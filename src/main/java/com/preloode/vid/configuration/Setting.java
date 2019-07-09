package com.preloode.vid.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class Setting {


    @Value("${setting.author}")
    private String author;

    private String content;

    private ArrayList<String> css;

    private String description;

    private String favicon;

    private ArrayList<String> javascript;

    private String logo;

    @Value("${setting.meta.description}")
    private String metaDescription;

    @Value("${setting.meta.keyword}")
    private String metaKeyword;

    @Value("${setting.meta.title}")
    private String metaTitle;

    @Value("${setting.name}")
    private String name;

    @Value("${setting.publisher}")
    private String publisher;


    public String getAuthor() {

        return this.author;

    }


    public void setAuthor(String author) {

        this.author = author;

    }


    public String getContent() {

        return this.content;

    }


    public void setContent(String content) {

        this.content = content;

    }


    public ArrayList<String> getCss() {

        return this.css;

    }


    public void setCss(ArrayList<String> css) {

        this.css = css;

    }


    public String getDescription() {

        return this.description;

    }


    public void setDescription(String description) {

        this.description = description;

    }


    public String getFavicon() {

        return this.favicon;

    }


    public void setFavicon(String favicon) {

        this.favicon = favicon;

    }


    public ArrayList<String> getJavascript() {

        return this.javascript;

    }


    public void setJavascript(ArrayList<String> javascript) {

        this.javascript = javascript;

    }


    public String getLogo() {

        return this.logo;

    }


    public void setLogo(String logo) {

        this.logo = logo;

    }


    public String getMetaDescription() {

        return this.metaDescription;

    }


    public void setMetaDescription(String metaDescription) {

        this.metaDescription = metaDescription;

    }


    public String getMetaKeyword() {

        return this.metaKeyword;

    }


    public void setMetaKeyword(String metaKeyword) {

        this.metaKeyword = metaKeyword;

    }


    public String getMetaTitle() {

        return this.metaTitle;

    }


    public void setMetaTitle(String metaTitle) {

        this.metaTitle = metaTitle;

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getPublisher() {

        return this.publisher;

    }


    public void setPublisher(String publisher) {

        this.publisher = publisher;

    }


}
