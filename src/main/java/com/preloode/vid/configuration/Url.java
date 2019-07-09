package com.preloode.vid.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Url {


    @Value("${url.audio}")
    private String audio;

    @Value("${url.base}")
    private String base;

    @Value("${url.css}")
    private String css;

    @Value("${url.font}")
    private String font;

    @Value("${url.image}")
    private String image;

    @Value("${url.javascript}")
    private String javascript;

    @Value("${url.panel}")
    private String panel;

    @Value("${url.plugin}")
    private String plugin;

    @Value("${url.website}")
    private String website;

    @Value("${url.video}")
    private String video;


    public String getAudio() {

        return this.audio;

    }


    public void setAudio(String audio) {

        this.audio = audio;

    }


    public String getBase() {

        return this.base;

    }


    public void setBase(String base) {

        this.base = base;

    }


    public String getCss() {

        return this.css;

    }


    public void setCss(String css) {

        this.css = css;

    }


    public String getFont() {

        return this.font;

    }


    public void setFont(String font) {

        this.font = font;

    }


    public String getImage() {

        return this.image;

    }


    public void setImage(String image) {

        this.image = image;

    }


    public String getJavascript() {

        return this.javascript;

    }


    public void setJavascript(String javascript) {

        this.javascript = javascript;

    }


    public String getPanel() {

        return this.panel;

    }


    public void setPanel(String panel) {

        this.panel = panel;

    }


    public String getPlugin() {

        return this.plugin;

    }


    public void setPlugin(String plugin) {

        this.plugin = plugin;

    }


    public String getWebsite() {

        return this.website;

    }


    public void setWebsite(String website) {

        this.website = website;

    }


    public String getVideo() {

        return this.video;

    }


    public void setVideo(String video) {

        this.video = video;

    }


}
