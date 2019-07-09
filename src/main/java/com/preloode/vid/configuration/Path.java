package com.preloode.vid.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Path {


    @Value("${path.audio}")
    private String audio;

    @Value("${path.css}")
    private String css;

    @Value("${path.font}")
    private String font;

    @Value("${path.image}")
    private String image;

    @Value("${path.javascript}")
    private String javascript;

    @Value("${path.plugin}")
    private String plugin;

    @Value("${path.video}")
    private String video;


    public String getAudio() {

        return this.audio;

    }


    public void setAudio(String audio) {

        this.audio = audio;

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


    public String getPlugin() {

        return this.plugin;

    }


    public void setPlugin(String plugin) {

        this.plugin = plugin;

    }


    public String getVideo() {

        return this.video;

    }


    public void setVideo(String video) {

        this.video = video;

    }


}
